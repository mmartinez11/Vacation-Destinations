# Application 1 (A1)
When the button is pressed the activity checks if the user has provided the permission to a dangerous permission.
If it does not have permission it will ask for it. When the permission is granted **Application 2** will be lunched.
Its second activity can only be lunched by **A3**.

![A1](https://user-images.githubusercontent.com/33674827/104112922-0539c980-52ba-11eb-8139-eac110aa5f9a.PNG)

# Application 2 (A2)
When the button is pressed the main activity will check if it was granted the same permission as **A1**. If it has then **Application 3** will be lunched.
If it does not have the permission then it will ask the user to grant the permission. If the permission is denied then the application will display a toast message
and self terminate.

![A2](https://user-images.githubusercontent.com/33674827/104112927-1256b880-52ba-11eb-9e9b-11987fc6b468.PNG)


# Application 3 (A3)
This application consists of two fragments. One fragment is a list view of clickable vacation destinations, and the second fragment displays an image of the destination.
In addition there is an action bar with two options. The first option lunches applications **A1** and **A2**. The application **A2** will lunch first displaying a
toast message. Then **A1** is lunched and it will display its second activity which is a web-view that displays the webpage of the selected vacation destination. 
The second option terminates the application. 

![A21](https://user-images.githubusercontent.com/33674827/104112934-1c78b700-52ba-11eb-8bac-91afc379569d.PNG)

![A11](https://user-images.githubusercontent.com/33674827/104112948-35816800-52ba-11eb-9bde-62f084fe9ddd.PNG)
