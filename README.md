![image](https://github.com/rowanxgamal/XMail/assets/110842052/49d2e67a-6ad3-47ff-8866-4135a344562f)# XMail: 
  ## A Mail Server Web Application.

  ## Table of Contents

- [Features](#Features)
- [Installation](#Installation)
- [Design Patterns](#Design-Patterns)
- [UML](#UML)
- [User Interface](#User-Interface)
- [Tutorial](#Tutorial)
  
  ### Features:
  
  - User authentication and authorization:
    - Sign Up
    - Login
  
  - Mail Features:
    - Composing mails
      -  creating new mails with its content ( Recievers, Atatchments, subject, Body, ...).
    - Sending mails
      - Single Reciever: sending mail to one person.
      - Multiple Recievers:  sending mail to many persons at once.
   
    - Drafting mails
      - Save current composed mail without sending it to send it latter
      - Edit or delete Draft Mails is available.
    - Trashing mails
      - Delete one or more mails at once.
      - Restoring deleted mails within 30 days of its deletion.
    - Folder Features
      - Having mails organised in folders
      - Creating Custom Folders
      - Editing, adding, moving any mail from one folder to another (Including our default primary folders 'Send, Draft, Inbpx')
    - Sorting:
      - User can sort mails in any folder according to 2 criterions:
        - Modify Date (Default)
        - Mail Priority (1 is the highest).
    - Contacts Features:
      - Creating Contacts
      - Renaming, adding emails to the same contact, and editing contacts
      - Sorting Contacts (Default / Alphabetically)
    - Search Functionality
      - Search without determining any filter:
      - Searches in all of our criteria: (Date, Sender, Receivers, Priority, Subject, Content, Attachments, Year )
      - Search is not static, any part of the string would still work
    - Filtering:
      - Single Filtering/ Multi-filtering on all of our criteria: (Date, Sender, Receivers, Priority, Subject, Content, Attachments, Year)
      - Allowing of multi filtering (AND criteria)
      - Users can add filters as much as they want, all would be filtered accordingly
      - Bulk move/select filtered mails and move to/create a new folder
  - User Interface Features:
    - Routing
    - Pagination in every folder
    - Scroll bar for extra terms in the sidebar
    - Icons for delete, restore, etc.
    - More discussed in design decisions.
    - Website User handling:
      - By the use of caching, we can allow users to use our system at the same time (but on different browsers of course)

  ### Installation:
  •  Front-End
    ○  Based on Vue js and java script functionalities.
  •  Back-End
    ○  Based on Spring boot Java
  •  Data Base
    ○  Json Files: A whole File to each user has his information, mails, folders, contacts.
  
  ### Design-Patterns:
  • Factory:
      (in Folder Creation) Since the user can have multiple folders, we needed a Creational design pattern that creates objects upon runtime, because we don’t exactly know the type of class needed.
  • Singleton:
      The singleton pattern ensures that a class has only one instance and provides a global point of access to that instance. We used singleton in all folders’ creation for every single user, since per user we want to ensure one folder is not instantiated every time the user wants to add an email to it.
  • Builder
      The builder pattern is used to construct complex objects, allowing for the creation of different configurations of a mail object without the need for a large  number of constructors or complex parameter lists. 
      This pattern is especially used for mail class, when a mail object has optional parameters, attachments, or varying formats, providing a clear and flexible way to create different types of mail instances while improving readability and maintainability in the code.
  • Filter
      The filter design pattern is used to enable the efficient and flexible filtering of objects based on various criteria without modifying the underlying object structure.
      In the context of filtering an array of mail objects, this pattern allows users to apply different filters (such as sender, date, subject, etc.) dynamically andcombine them as needed, providing a modular and extensible way to manage complex filtering requirements without altering the core logic of the mail objects or the filtering process.
      The filter also helped us specifically in using its Criteria for general search in our mail server
  • Factory (in Strategy Sort)
    Factory for the creation of Sort class which sorts the list of mails.
  • Proxy
    Proxy for Xmail to prevent everyone from having access to the system unless he is already signed in.
  • Strategy
    The strategy design pattern is used in the context of sorting an array of mail objects to define a family of algorithms (strategies) for sorting and makingthem interchangeable. This allows the client code to dynamically select and apply a sorting strategy based on priorities or default criteria without modifying the sorting logic in the mail objects themselves.  

## UML
![image](https://github.com/rowanxgamal/XMail/assets/110842052/177f0ba0-cbc3-4228-a7b1-06b65f70b67b)

![image](https://github.com/rowanxgamal/XMail/assets/110842052/122b1f55-accf-435b-a393-181c7bdb5bb5)

## User-Interface
Login
![image](https://github.com/rowanxgamal/XMail/assets/110842052/15f6caa7-9d31-457f-bdda-b71496ee97df)

Sign-Up
![image](https://github.com/rowanxgamal/XMail/assets/110842052/c23028bf-5032-4305-a236-cf0432390140)

Home
![image](https://github.com/rowanxgamal/XMail/assets/110842052/35ceda2c-361b-4af9-ba0b-df89e504e58c)


## Contributeres
### Front-End
- [Youssef Mahmoud](https://github.com/Youssef-Mahmoud0)
- [Youmna Yasser](https://github.com/yomnay888)
### Back-End
- [Mustafa ElKaranshawy](https://github.com/MostafaElKaranshawy)
- [Rowan Gamal](https://github.com/rowanxgamal)
