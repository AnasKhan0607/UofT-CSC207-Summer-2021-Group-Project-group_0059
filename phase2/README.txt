+About our program

Our program is a game editor specially made for the creation of visual novel games. Categorized by theme: there are 3 branches - User, Game, and Template. Where User is mostly about login, signup, and messaging between users. Game is everything associated with playing, creating, and editing games. Last but not least, the Template which is about the template that is used to create a Game, and here we allows admin users to edit templates. And we actually divided the job this way, into: User Branch, Game Branch, and Template Branch.

+Instruction on running
Since the presentation, we have added SQL database to the program (For saving Games and Templates).
To run the program successfully (specifically to access the database feature), MySQL Community Edition MAY need to be installed.
//Project Structure -> Module -> + -> Add the jdbc connector(.jar in lib folder) in order to have the right driver identified
- Note that we have only run this program on Windows 10 (As all of us only have windows computers),
and the program may possibly not run on mac due to some difference in operating systems that we are not aware of.
- Also Note that loading and saving to database takes time.
- We recorded videos of the database working on windows 10 computer in case it doesn't on mac:
https://drive.google.com/file/d/1i4aC6YVzr_WUp5M4ZtuM8ioWTsd74zH5/view
https://drive.google.com/file/d/1POhkOIngfOenJ0S201NKMgCVi5OoQU75/view


Run Main.java and we will have the interface of Login. There we can choose to login with an account in record, signup one, login temporarily as a guest, or recover your password.
After logged in successfully, you have the option of creating/editing/playing a game, reading/sending messages, or resetting your password. And depending on whether is Admin or not, edit a Template and suspend and
unsuspend a user.

In the game GUI, there are 5 options. You can create a game, play a game that you created, edit a game that is created by you and also private, view games that are public, or private games created by you, and at last you can choose to exit the game GUI.
For Admin Users, other's private games can be seen in game edit option and can be either made public or deleted at Admin's will. And so can an Admin make a public game into a private one.

In the Template editing interface you choose one of the available templates and change one of the attributes of the template (name, description, number of choices, or scheme of the template).


design1.png  //the uml of Game
design2.png   //the uml of User,Message
design3.png  //the uml of Template
