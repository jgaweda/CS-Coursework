from PyQt5 import QtCore, QtGui, QtWidgets
import csv

from PyQt5.QtWidgets import QMessageBox

from Window_Employees import Ui_MainWindow
from Window_Companies import Ui_MainWindow_Company
from ScatterPlot_Employees import Ui_Employee_Scatter_Plot
from ScatterPlot_Companies import Ui_MainWindow_Company_Scatter_Plot

class Ui_MainWindow_employee_record(QtWidgets.QWidget):

    def __init__(self, fileName, parent=None):
        super(Ui_MainWindow_employee_record, self).__init__(parent)
        self.fileNameEmployee = "Data_Employee_Raw.csv"
        self.fileNameAlteredEmployee = "Data_Employee_Processed.csv"
        self.fileNameCompany = "Data_Company_Raw.csv"
        self.fileNameAlteredCompany = "Data_Company_Processed.csv"
        self.model = QtGui.QStandardItemModel(self)

        self.tableView = QtWidgets.QTableView(self)
        self.tableView.setModel(self.model)
        self.tableView.horizontalHeader().setStretchLastSection(True)
        self.tableView.setFixedSize(800, 600)

        self.load_employee_data_button = QtWidgets.QPushButton(self)
        self.load_employee_data_button.setText("Click to view raw employee data")
        try:
            self.load_employee_data_button.clicked.connect(self.on_pushButtonLoad_clicked_employee)
        except EnvironmentError:
            msg = QMessageBox()
            msg.setIcon(QMessageBox.Critical)
            msg.setText("Error! Please close existing window.")
            msg.setInformativeText('Only one window may be open at a time. Close existing window to continue!')
            msg.setWindowTitle("Error")
            msg.exec_()
            pass
        self.load_altered_employee_data_button = QtWidgets.QPushButton(self)
        self.load_altered_employee_data_button.setText("Click to view processed employee data")
        self.load_altered_employee_data_button.setGeometry(QtCore.QRect(11, 645, 800, 25))
        self.load_altered_employee_data_button.clicked.connect(self.on_pushButtonLoad_clicked_altered_employee)

        self.load_company_data_button = QtWidgets.QPushButton(self)
        self.load_company_data_button.setText("Click to view raw company data")
        self.load_company_data_button.setGeometry(QtCore.QRect(11, 675, 800, 25))
        self.load_company_data_button.clicked.connect(self.on_pushButtonLoad_clicked_company)

        self.load_altered_company_data_button = QtWidgets.QPushButton(self)
        self.load_altered_company_data_button.setText("Click to view processed company data")
        self.load_altered_company_data_button.setGeometry(QtCore.QRect(11, 705, 800, 25))
        self.load_altered_company_data_button.clicked.connect(self.on_pushButtonLoad_clicked_altered_company)

        self.layoutVertical = QtWidgets.QVBoxLayout(self)
        self.layoutVertical.addWidget(self.tableView)
        self.layoutVertical.addWidget(self.load_employee_data_button)
        self.layoutVertical.addSpacing(220)
        self.welcomeLabel = QtWidgets.QLabel(self)
        self.welcomeLabel.setGeometry(QtCore.QRect(215, 735, 500, 25))
        font = QtGui.QFont()
        font.setFamily("Courier")
        font.setPointSize(16)
        self.welcomeLabel.setFont(font)
        self.welcomeLabel.setText("Epsilon Delta Privacy Solution")
        self.navigationLabel = QtWidgets.QLabel(self)
        self.navigationLabel.setGeometry(QtCore.QRect(125, 760, 700, 25))

        self.navigationLabel.setFont(font)
        self.navigationLabel.setText("__________________________________________")

        self.employee_record_button = QtWidgets.QPushButton(self)
        self.employee_record_button.setText("Apply EDP to Employee Records")
        self.employee_record_button.setGeometry(QtCore.QRect(11, 800, 450, 25))
        self.employee_record_button.clicked.connect(self.employee_one_openWindow)

        self.employee_scatter_plot_button = QtWidgets.QPushButton(self)
        self.employee_scatter_plot_button.setText("View scatter plot comparing Employee Records")
        self.employee_scatter_plot_button.setGeometry(QtCore.QRect(11, 830, 450, 25))
        self.employee_scatter_plot_button.clicked.connect(self.employee_one_scatter_plot_openWindow)

        self.company_record_button = QtWidgets.QPushButton(self)
        self.company_record_button.setText("Apply EDP to Company Records")
        self.company_record_button.setGeometry(QtCore.QRect(420, 800, 380, 25))
        self.company_record_button.clicked.connect(self.openWindow_company1)

        self.company_scatter_plot_button = QtWidgets.QPushButton(self)
        self.company_scatter_plot_button.setText("View scatter plot comparing Company Records")
        self.company_scatter_plot_button.setGeometry(QtCore.QRect(420, 830, 380, 25))
        self.company_scatter_plot_button.clicked.connect(self.company_one_scatter_plot_openWindow)



    def loadCsv(self, fileName):
        self.model.clear()
        with open(fileName, "r") as fileInput:
            for row in csv.reader(fileInput):
                items = [
                    QtGui.QStandardItem(field)
                    for field in row
                ]
                self.model.appendRow(items)

    @QtCore.pyqtSlot()
    def on_pushButtonLoad_clicked_employee(self):
        self.loadCsv(self.fileNameEmployee)

    @QtCore.pyqtSlot()
    def on_pushButtonLoad_clicked_altered_employee(self):
        self.loadCsv(self.fileNameAlteredEmployee)

    @QtCore.pyqtSlot()
    def on_pushButtonLoad_clicked_company(self):
        self.loadCsv(self.fileNameCompany)

    @QtCore.pyqtSlot()
    def on_pushButtonLoad_clicked_altered_company(self):
        self.loadCsv(self.fileNameAlteredCompany)

    def employee_one_openWindow(self):
        self.window = QtWidgets.QMainWindow()
        self.ui = Ui_MainWindow()
        self.ui.setupUi(self.window)
        self.window.show()

    def altered_employee_one_openWindow(self):
        self.window = QtWidgets.QMainWindow()
        self.ui = Ui_MainWindow()
        self.ui.setupUi(self.window)
        self.window.show()

    def employee_one_scatter_plot_openWindow(self):
        self.window = QtWidgets.QMainWindow()
        self.ui = Ui_Employee_Scatter_Plot()
        self.ui.setupUi(self.window)
        self.window.show()

    def openWindow_company1(self):
        self.window = QtWidgets.QMainWindow()
        self.ui = Ui_MainWindow_Company()
        self.ui.setupUi(self.window)
        self.window.show()

    def openWindow_altered_company1(self):
        self.window = QtWidgets.QMainWindow()
        self.ui = Ui_MainWindow_Company()
        self.ui.setupUi(self.window)
        self.window.show()


    def company_one_scatter_plot_openWindow(self):
        self.window = QtWidgets.QMainWindow()
        self.ui =  Ui_MainWindow_Company_Scatter_Plot()
        self.ui.setupUi(self.window)
        self.window.show()


if __name__ == "__main__":
    import sys

    app = QtWidgets.QApplication(sys.argv)
    app.setApplicationName('Epsilon Delta Privacy Solution')

    main = Ui_MainWindow_employee_record("data.csv")
    main.show()

    altered_employee_data = "Data_Employee_Processed.csv"
    f = open(altered_employee_data, "w+")
    f.close()

    altered_company_data = "Data_Company_Processed.csv"
    f = open(altered_company_data, "w+")
    f.close()

    sys.exit(app.exec_())
