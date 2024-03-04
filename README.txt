C195 Project - For Software II - Advanced Java Concepts
This application was built to give a user access to View, Add, Update, and Delete Customers and Appointmnets from a company database.
This also gives some functionality to view reporting and provides logic to ensure appointmnets are not overlapping when created and start and end within business hours.

Author: Christopher Coluccio
Contact: ccolu4@wgu.edu
Application version 1.0
Date: 3/1/2024
IntelliJ IDEA 2023.2 (Community Edition)
Runtime version: 17.0.7+7-b1000.6 amd64
JavaFX version: 17.0.6

#################################################################
Directions for Application Operation:
Upon launch, the User is directed to a Log In screen.
User will enter a valid Username and Password and click Log In

User arrives at Main Menu Dashboard from which they have the following options:

Search Customers:
Input Substring of Customer Name or Customer ID and press Enter to filter the shown list.

Select Customer (Click a row on the list):
	Show Customer Appointments:
	This will filter the Appointments table to show the selected Customer's appointmnets

	Update Customer:
	Brings user to the Update Customer Screen

	Delete Customer:
	If Customer has no appointments, system confirms and deletes

Filter Appointments (Radio Buttons):
	All:
	Shows all appointments

	Month:
	Shows all appointments in the current month

	Week:
	Shows all appointmnets in the current week (Most recent Sun to upcoming Sat)

Select Appointment:
	Update Appointment:
	Brings user to Update Appointment Screen

	Delete Appointment:
	System confirms and deletes

Reporting:
	Brings User to Reporting Screen

Log Out:
	Brings User to Log In Screen

Add Customer:
	User needs to fill in all text fields and select dropdowns. Save will create a new customer, barring any invalid input. Cancel cancels.

Update Customer:
	User is able to change all fields other than ID. Save submits these changes, barring any invalid input. Cancel keeps everything the way it was before entering this screen.

Add Appointment:
	User needs to fill in all text fields and select dropdowns. Save will create a new appointment, barring any invalid input. Appointment must be today or later. Customers cannot have overlapping appointments and appointments must start and stop withing business hours. Cancel cancels.

Add Appointment:
	User can update any text fields and dropdowns. Save will update appointmnet, barring any invalid input. Appointment must be today or later. Customers cannot have overlapping appointments and appointments must start and stop withing business hours. Cancel cancels.

Reporting:
	User can select different filters to review aggregated totals of groups of Appointments & Customers. 
	By Month:
	Shows user totals of appointments in each month-year

	By Type:
	Shows user totals of appointments by each type

	By State/Region:
	Shows user totals of customers by First Level Division

	View:
	Once the user selects a contact, they may View that contact's list of appointments

	Main Menu:
	Returns to Main Menu Dashboard

View:
	View:
	User may change their selection and view a different contact's schedule once selected

	Main Menu:
	Returns to Main Menu Dashboard

#################################################################


A3f: Additional Report

	By State/Region:
	Shows user totals of customers by First Level Division

This Report is useful to a user who may want to have any idea of their customer distribution by state & region. This could help a user determine the performance of their business in different locations. The total number of customers for each First Level Division is shown in a similar fashion to the other reports available.

MYSQL Community Server GPL - 8.0.26