Please follow the steps below to configure and run the application:
1.	Unzip the attached Compressed folder and place the “Project Files” folder on your desktop.
2.	Verify that Python 3.9 is installed.  You can do this by opening a terminal window and entering “python -version”.  If you find that Python is not installed, please see “Installing Python” section below. 
3.	Open the “Project Files” folder in the Python IDE of your choice.  Development was completed on PyCharm Community Edition, available free at https://www.jetbrains.com/pycharm/download/#section=windows.  
4.	Install the required Python Libraries.(PyQtGraph and PyQt5)
•	If using PyCharm, this can be done by going to File>>Settings>>Project:Project Files>>Python Interpreter.  Click the “+” in the lower left-hand corner of the Python Library grid, as seen in figure 8. In the new window, search for the packages by name and then install. More detailed instructions available at https://www.jetbrains.com/help/pycharm/installing-uninstalling-and-upgrading-packages.html#install-package                                                                    
•	 If unable to find the libraries using your IDE please see “Python Troubleshooting” section below.
5.	Locate the project file “Main.py”.
6.	Right-click on “Main.py” and select “Run”.
7.	When the main EDPS window is open, click the four buttons at the top to analyze each dataset.  Please Note: The processed dataset will be empty until the accompanying raw dataset is filtered.
8.	Click the ‘View Scatterplot’ for a dataset to view raw data points.  Please be sure to close the scatterplot window before continuing. This can be done by clicking the red “X” in the upper right-hand corner of the scatterplot window.
9.	Click the ‘Apply EDP’ button for the accompanying dataset you would like to process.
10.	 Select one or two fields to filter and then click the “Apply Epsilon Delta Privacy Filtering” button.
11.	Review the percentage change to each dataset field and the overall dataset. Please be sure to close the data filter window before continuing. This can be done by clicking the red “X” in the upper right-hand corner of the window.
Python Troubleshooting
1.	If you find that Python is not installed, navigate to https://www.journaldev.com/30076/install-python-windows-10 to correctly install Python and configure the correct path.
2.	If you are unable to find the required Python libraries, the packages can be found at https://riverbankcomputing.com/software/pyqt/download. 
