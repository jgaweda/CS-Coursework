from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtWidgets import QMessageBox
from pyqtgraph import PlotWidget
import pyqtgraph as pg
import csv
import string
from collections import Counter
from datetime import datetime

company_name_value_array = []
altered_company_name_value_array = []


def read_company_records():
    with open('Data_Company_Raw.csv') as csvfile:
        unaltered_records = []
        readCSV = csv.reader(csvfile, delimiter=',')
        for row in readCSV:
            unaltered_records.append(row[1][1])
    company_name_records = Counter(unaltered_records)
    alphabets = list(string.ascii_lowercase)
    output = []
    for a in alphabets:
        output.append(a)
    for i in output:
        company_name_value_array.append((company_name_records[i]))


def read_altered_company_records():
    with open('Data_Company_Processed.csv') as csvfile:
        altered_records = []
        readCSV = csv.reader(csvfile, delimiter=',')
        for row in readCSV:
            altered_records.append(row[1][1])
    company_name_records = Counter(altered_records)
    alphabets = list(string.ascii_lowercase)
    output = []
    for a in alphabets:
        output.append(a)
    for i in output:
        altered_company_name_value_array.append((company_name_records[i]))


class Ui_MainWindow_Company_Scatter_Plot(object):
    def setupUi(self, MainWindow):
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(800, 650)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setObjectName("centralwidget")

        self.label = QtWidgets.QLabel(self.centralwidget)
        self.label.setGeometry(QtCore.QRect(10, 600, 850, 31))
        font = QtGui.QFont()
        font.setPointSize(10)
        self.label.setFont(font)
        self.label.setObjectName("label")

        read_company_records()
        read_altered_company_records()
        alphabet = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26]
        unaltered_employee_names = company_name_value_array
        altered_employee_names = altered_company_name_value_array

        self.graphWidget = PlotWidget(self.centralwidget)
        self.graphWidget.setGeometry(QtCore.QRect(0, 0, 785, 580))
        self.graphWidget.setObjectName("graphWidget")
        MainWindow.setCentralWidget(self.centralwidget)
        self.statusbar = QtWidgets.QStatusBar(MainWindow)
        self.statusbar.setObjectName("statusbar")
        MainWindow.setStatusBar(self.statusbar)

        self.graphWidget.setLabel('left', 'Number of occurrences of company names first letter values', color='orange', size=30)
        self.graphWidget.setLabel('bottom', 'Company Names sorted by first letter in a companies name', color='orange', size=30)

        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)
        self.graphWidget.addLegend()
        self.graphWidget.showGrid(x=True, y=True)
        self.graphWidget.setXRange(0.5, 26.5, padding=0)
        self.graphWidget.setYRange(0, 300, padding=0)
        self.plot(alphabet, unaltered_employee_names, "Original Company Names", 'r')
        self.plot(alphabet, altered_employee_names, "Altered Company Names", 'g')

    def plot(self, x, y, plotname, color):
        pen = pg.mkPen(color=color)
        z = y
        if len(y) > len(x):
            z = y[26:52]
            x.extend(range(len(x)+1, len(z)+1))
        self.graphWidget.plot(x, z, name=plotname, pen=pen, symbol='+', symbolSize=10, symbolBrush=(color))

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "Epsilon Delta Privacy Solution"))
        self.label.setText(_translate("MainWindow",
                                      "NOTE: This graph displays the difference between company names if the Epsilon Delta Privacy Solution includes the company name values."))


if __name__ == "__main__":
    import sys
    app = QtWidgets.QApplication(sys.argv)
    app.setApplicationName('Epsilon Delta Privacy Solution')
    MainWindow = QtWidgets.QMainWindow()
    ui = Ui_MainWindow_Company_Scatter_Plot()
    ui.setupUi(MainWindow)
    MainWindow.show()
    sys.exit(app.exec_())
