from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtWidgets import  QMessageBox
from HashMap_Employees import Employee_HashMap
import numpy as np
import csv
import random

noise_counter_one = 0
noise_counter_two = 0
insert_into_hash_table = Employee_HashMap()

def employee_names():
    employee_array = np.arange(1, 101)
    employee_name_array = [None]
    for i in range(len(employee_array)):
        try:
            result = random.randint(1, 1000)
            employee_name_array[i] = get_hashmap().get(str(result))[1]
        except IndexError:
            pass
    format_list = str(employee_name_array)[2:-2]
    return format_list


def employee_emails():
    employee_array = np.arange(1, 101)
    employee_name_array = [None]
    for i in range(len(employee_array)):
        try:
            result = random.randint(1, 1000)
            employee_name_array[i] = get_hashmap().get(str(result))[3]
        except IndexError:
            pass
    format_list = str(employee_name_array)[2:-2]
    return format_list


def update_counter1_noise():
    global noise_counter_one
    noise = noise_counter_one + 1
    noise_counter_one = noise
    return noise


def update_counter2_noise():
    global noise_counter_two
    noise = noise_counter_two + 1
    noise_counter_two = noise
    return noise


def get_hashmap():
    return insert_into_hash_table


def read_records_employee():
    with open('Data_Employee_Raw.csv') as csvfile:
        readCSV = csv.reader(csvfile, delimiter=',')

        for row in readCSV:
            employee_id = row[0]
            employee_first_name = row[1]
            employee_last_name = row[2]
            employee_email = row[3]
            employee_gender = row[4]
            employee_job_title = row[5]
            employee_record = [employee_id, employee_first_name, employee_last_name, employee_email, employee_gender, employee_job_title]

            key = employee_id
            value = employee_record

            insert_into_hash_table.insert(key, value)

def write_records_employee():
    with open('Data_Employee_Processed.csv', mode='w', newline='') as csvfile:
        writeCSV = csv.writer(csvfile, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
        test_array = np.arange(1, 1002)
        try:
            for i in range(len(test_array)):
                if i == 0:
                    pass
                else:
                    writeCSV.writerow(get_hashmap().get(str(i)))
        except IndexError:
            pass


def data_filter1_employee_names(raw_data, index, updated_field):
    res = random.randint(0, 1)
    if res == 0:
        pass
    else:
        res = random.randint(0, 1)
        if res == 0:
            pass
        else:
            raw_data = employee_names()
            get_hashmap().get(str(index))[updated_field] = raw_data
            update_counter1_noise()


def data_filter1_employee_emails(raw_data, index, updated_field):
    result = random.randint(0, 1)
    if result == 0:
        pass
    else:
        result = random.randint(0, 1)
        if result == 0:
            pass
        else:
            raw_data = employee_emails()
            get_hashmap().get(str(index))[updated_field] = raw_data
            update_counter1_noise()


def data_filter2_employee_names(raw_data, index, updated_field):
    result = random.randint(0, 1)
    if result == 0:
        pass
    else:
        result = random.randint(0, 1)
        if result == 0:
            pass
        else:
            result = random.randint(0, 1)
            if result == 0:
                pass
            else:
                raw_data = employee_names()
                get_hashmap().get(str(index))[updated_field] = raw_data
                update_counter2_noise()


def data_filter2_employee_emails(raw_data, index, updated_field):
    result = random.randint(0, 1)
    if result == 0:
        pass
    else:
        result = random.randint(0, 1)
        if result == 0:
            pass
        else:
            result = random.randint(0, 1)
            if result == 0:
                pass
            else:
                raw_data = employee_emails()
                get_hashmap().get(str(index))[updated_field] = raw_data
                update_counter2_noise()



class Ui_MainWindow(object):

    test_array = np.arange(1, 1001)

    def setupUi(self, MainWindow):
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(593, 393)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setObjectName("centralwidget")
        self.first_filter_label = QtWidgets.QLabel(self.centralwidget)
        self.first_filter_label.setGeometry(QtCore.QRect(10, 30, 391, 61))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.first_filter_label.setFont(font)
        self.first_filter_label.setObjectName("first_filter_label")
        self.first_privacy_field_combo_box = QtWidgets.QComboBox(self.centralwidget)
        self.first_privacy_field_combo_box.setGeometry(QtCore.QRect(440, 40, 141, 31))
        self.first_privacy_field_combo_box.setObjectName("first_privacy_field_combo_box")
        self.first_privacy_field_combo_box.addItem("")
        self.first_privacy_field_combo_box.addItem("")
        self.first_privacy_field_combo_box.addItem("")
        self.label_2 = QtWidgets.QLabel(self.centralwidget)
        self.label_2.setGeometry(QtCore.QRect(210, 10, 161, 21))
        font = QtGui.QFont()
        font.setPointSize(14)
        self.label_2.setFont(font)
        self.label_2.setObjectName("label_2")
        self.second_filter_label = QtWidgets.QLabel(self.centralwidget)
        self.second_filter_label.setGeometry(QtCore.QRect(10, 100, 421, 61))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.second_filter_label.setFont(font)
        self.second_filter_label.setObjectName("second_filter_label")
        self.second_privacy_field_combo_box = QtWidgets.QComboBox(self.centralwidget)
        self.second_privacy_field_combo_box.setGeometry(QtCore.QRect(440, 110, 141, 31))
        self.second_privacy_field_combo_box.setObjectName("second_privacy_field_combo_box")
        self.second_privacy_field_combo_box.addItem("")
        self.second_privacy_field_combo_box.addItem("")
        self.second_privacy_field_combo_box.addItem("")
        self.DP_button = QtWidgets.QPushButton(self.centralwidget)
        self.DP_button.setGeometry(QtCore.QRect(10, 160, 571, 31))
        self.DP_button.setObjectName("DP_button")

        self.DP_button.clicked.connect(self.pressed)

        self.first_data_set_label = QtWidgets.QLabel(self.centralwidget)
        self.first_data_set_label.setGeometry(QtCore.QRect(10, 200, 561, 41))
        font = QtGui.QFont()
        font.setPointSize(14)
        self.first_data_set_label.setFont(font)
        self.first_data_set_label.setObjectName("first_data_set_label")
        self.second_data_set_label = QtWidgets.QLabel(self.centralwidget)
        self.second_data_set_label.setGeometry(QtCore.QRect(10, 240, 561, 41))
        font = QtGui.QFont()
        font.setPointSize(14)
        self.second_data_set_label.setFont(font)
        self.second_data_set_label.setObjectName("second_data_set_label")
        self.third_data_set_label = QtWidgets.QLabel(self.centralwidget)
        self.third_data_set_label.setGeometry(QtCore.QRect(10, 280, 561, 41))
        font = QtGui.QFont()
        font.setPointSize(14)
        self.third_data_set_label.setFont(font)
        self.third_data_set_label.setObjectName("third_data_set_label")
        self.main_menu_text = QtWidgets.QLabel(self.centralwidget)
        self.main_menu_text.setGeometry(QtCore.QRect(10, 320, 581, 16))
        font = QtGui.QFont()
        font.setPointSize(9)
        self.main_menu_text.setFont(font)
        self.main_menu_text.setObjectName("main_menu_text")

        MainWindow.setCentralWidget(self.centralwidget)
        self.statusbar = QtWidgets.QStatusBar(MainWindow)
        self.statusbar.setObjectName("statusbar")
        MainWindow.setStatusBar(self.statusbar)

        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "Epsilon Delta Privacy Solution"))
        self.first_filter_label.setText(_translate("MainWindow", "Please enter the first field to apply privacy filtering on:"))
        self.first_privacy_field_combo_box.setItemText(0, _translate("MainWindow", "First Name"))
        self.first_privacy_field_combo_box.setItemText(1, _translate("MainWindow", "Last Name"))
        self.first_privacy_field_combo_box.setItemText(2, _translate("MainWindow", "Email"))
        self.label_2.setText(_translate("MainWindow", "Employee Records "))
        self.second_filter_label.setText(_translate("MainWindow", "Please enter the second field to apply privacy filtering on:"))
        self.second_privacy_field_combo_box.setItemText(0, _translate("MainWindow", "First Name"))
        self.second_privacy_field_combo_box.setItemText(1, _translate("MainWindow", "Last Name"))
        self.second_privacy_field_combo_box.setItemText(2, _translate("MainWindow", "Email"))
        self.DP_button.setText(_translate("MainWindow", "Apply Epsilon Delta Privacy Filtering"))
        self.first_data_set_label.setText(_translate("MainWindow", "Percent change applied to first data set: 0.00%"))
        self.second_data_set_label.setText(_translate("MainWindow", "Percent change applied to second data set: 0.00%"))
        self.third_data_set_label.setText(_translate("MainWindow", "Total percent change applied to data set: 0.00%"))
        self.main_menu_text.setText(_translate("MainWindow", "To view the altered employee records dataset, return to the main menu"))

    def pressed(self):
        first_employee_filter = str(self.first_privacy_field_combo_box.currentText())
        second_employee_filter = str(self.second_privacy_field_combo_box.currentText())

        read_records_employee()
        test_array = np.arange(1, 1001)
        try:
            if first_employee_filter == "First Name":
                field_to_be_updated_one = 1
                for i in range(len(test_array)):
                    if i == 0:
                        pass
                    else:
                        data_filter1_employee_names(get_hashmap().get(str(test_array[i]))[1], test_array[i],
                                                    field_to_be_updated_one)
                self.first_data_set_label.setText("Change applied to first data set: " + "{:.2%}".format(
                    int(update_counter1_noise()) / len(test_array)))
            if first_employee_filter == "Last Name":
                field_to_be_updated_one = 2
                for i in range(len(test_array)):
                    if i == 0:
                        pass
                    else:
                        data_filter1_employee_names(get_hashmap().get(str(test_array[i]))[1], test_array[i],
                                                    field_to_be_updated_one)
                self.first_data_set_label.setText("Change applied to first data set: " + "{:.2%}".format(
                    int(update_counter1_noise()) / len(test_array)))
            if first_employee_filter == "Email":
                field_to_be_updated_one = 3
                for i in range(len(test_array)):
                    if i == 0:
                        pass
                    else:
                        data_filter1_employee_emails(get_hashmap().get(str(test_array[i]))[1], test_array[i],
                                                     field_to_be_updated_one)
                self.first_data_set_label.setText("Change applied to first data set: " + "{:.2%}".format(
                    int(update_counter1_noise()) / len(test_array)))
            if second_employee_filter == "First Name":
                field_to_be_updated_two = 1
                for i in range(len(test_array)):
                    if i == 0:
                        pass
                    else:
                        data_filter2_employee_names(get_hashmap().get(str(test_array[i]))[3], test_array[i],
                                                    field_to_be_updated_two)
                self.second_data_set_label.setText("Change applied to second data set: " + "{:.2%}".format(
                    int(update_counter2_noise()) / len(test_array)))
            if second_employee_filter == "Last Name":
                field_to_be_updated_two = 2
                for i in range(len(test_array)):
                    if i == 0:
                        pass
                    else:
                        data_filter2_employee_names(get_hashmap().get(str(test_array[i]))[3], test_array[i],
                                                    field_to_be_updated_two)
                self.second_data_set_label.setText("Change applied to second data set: " + "{:.2%}".format(
                    int(update_counter2_noise()) / len(test_array)))
            if second_employee_filter == "Email":
                field_to_be_updated_two = 3
                for i in range(len(test_array)):
                    if i == 0:
                        pass
                    else:
                        data_filter2_employee_emails(get_hashmap().get(str(test_array[i]))[3], test_array[i],
                                                     field_to_be_updated_two)
                self.second_data_set_label.setText("Change applied to second data set: " + "{:.2%}".format(
                    int(update_counter2_noise()) / len(test_array)))

            self.third_data_set_label.setText("Total change applied to data set: " + "{: .2%}".format(int(noise_counter_one + noise_counter_two) / 6000))

            altered_data = "Data_Employee_Processed.csv"
            f = open(altered_data, "w+")
            f.close()
            write_records_employee()

        except IndexError:
            msg = QMessageBox()
            msg.setIcon(QMessageBox.Critical)
            msg.setText("Error with data update.")
            msg.setInformativeText('You can only apply the noise to this data set once, please restart the application!')
            msg.setWindowTitle("Error")
            msg.exec_()
            pass


if __name__ == "__main__":
    import sys
    app = QtWidgets.QApplication(sys.argv)
    MainWindow = QtWidgets.QMainWindow()
    ui = Ui_MainWindow()
    ui.setupUi(MainWindow)
    MainWindow.show()
    sys.exit(app.exec_())
