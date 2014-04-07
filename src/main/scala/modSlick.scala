// Use H2Driver to connect to an H2 database
import scala.slick.driver.H2Driver.simple._

//TODO: Cap all the classes and query interfaces

//Magic stuff:
//("jdbc:h2:mem:hello", driver = "org.hy.driver")
//(reg_users.ddl ++ reg_events.ddl ++ sgroup.ddl ++ contacts.ddl ++ user_to_sgroup.ddl ++ listof_events.ddl)
//import Database.threadLocalSession

// The main application
object modSlick extends App {

  //The query interface for the * table
  val reg_users: TableQuery[reg_users] = TableQuery[reg_users]
  
  //The query interface for the * table
  val reg_events: TableQuery[reg_events] = TableQuery[reg_events]
  
  //The query interface for the * table
  val sgroup: TableQuery[sgroup] = TableQuery[sgroup]
  
  //The query interface for the * table
  val contacts: TableQuery[contacts] = TableQuery[contacts]
  
  //The query interface for the * table
  val user_to_sgroup: TableQuery[user_to_sgroup] = TableQuery[user_to_sgroup]
  
  //The query interface for the * table
  val listof_events: TableQuery[listof_events] = TableQuery[listof_events]
  
  //The query interface for the * table
  //val : TableQuery[] = TableQuery[] 
  
  
  //Create a connection (called a "session") to an in-memory H2 database
  Database.forURL("jdbc:h2:mem:insertname", driver = "org.h2.Driver").withSession { implicit session =>
  
  //Create the schema by combining the DDLs for the tables using the query interfaces
  //(reg_users.ddl ++ reg_events.ddl ++ sgroup.ddl ++ contacts.ddl ++ user_to_sgroup.ddl ++ listof_events.ddl).create
   (reg_users.ddl  ++ reg_events.ddl ++ sgroup.ddl ++ contacts.ddl ++ user_to_sgroup.ddl ++ listof_events.ddl).create
  
  // Create / Insert
  
  //Insert some users individually
  reg_users += (1, "paul", "iamawesome", "123@123.com")
  reg_users += (2, "laura", "123", "123@123.com")
  reg_users += (3, "molly", "123", "123@123.com")
  reg_users += (4, "jasper", "123", "123@123.com")
  reg_users += (5, "sat", "123", "123@123.com")
  
  //Insert some events
  val eventsInsert: Option[Int] = reg_events ++= Seq(
     (1, "TacoBell","PartyHard","Party with TacoBell","Balboa","San Diego","CA","92101", 0,21,0,0,0,0),
     (2, "EarthBending Match","PartyHard","Party with TacoBell","Balboa","San Diego","CA","92101", 0,21,0,0,0,0), 
     (3, "Star Trek","PartyHard","Party with TacoBell","Balboa","San Diego","CA","92101", 0,21,0,0,0,0), 
     (4, "Comic Con","PartyHard","Party with TacoBell","Balboa","San Diego","CA","92101", 0,21,0,0,0,0),
     (5, "Chargers","PartyHard","Party with TacoBell","Balboa","San Diego","CA","92101", 0,21,0,0,0,0)
    )
  
  //Insert some users individually
  val sgroupInsert: Option[Int] = sgroup ++= Seq(
     (1, 2, "Molly's Group"),
     (2,1,"Paul's Group")      
    )  
  
 // Insert some contacts
  val contactsInsert: Option[Int] = contacts ++= Seq(
      (1,1,2),
      (2,2,1),
      (3,2,3),
      (4,3,2),
      (5,3,4),
      (6,4,3),
      (7,4,1),
      (8,1,4),
      (9,2,4),
      (10,4,2),
      (11,5,2),
      (12,2,5),
      (13,1,5),
      (14,5,1)
      )
  
  //Insert people into the user_to_group
  val user_to_sgroup_insert: Option[Int] = user_to_sgroup ++= Seq(
    (1,1,2),
    (2,1,4),
    (3,2,2),
    (4,2,4),
    (5,2,5)
    )
  
  //Insert events into the listof_events
  val Events_to_sgroup_insert: Option[Int] = listof_events ++= Seq(
    (1,1,1),
    (2,1,4),
    (3,2,3),
    (4,2,4),
    (5,2,5)
    )
    
  /*
  
  //val composedQuery
  val namesQuery: Query[Column [String], String] = reg_users.sortBy(_.user_name).map(_.user_name)
  println(namesQuery.list)
  
  //Construct query finding events
  val eventQuery: Query[Column[String], String] = reg_events.sortBy(_.reg_events_id).map(_.reg_events_title)
  println(eventQuery.list)
  
  //Constrct query finding sgroup
  val sgroupQuery: Query[Column[Int], Int] = sgroup.sortBy(_.sgroup_id).map(_.sgroup_lead)
  println(sgroupQuery.list)
  
  //Construct query finding contacts of user
  val contactsQuery: Query[Column[Int], Int] = contacts.filter(_.contacts_owner_id === 2).map(_.contactto_owner_id)
  println(contactsQuery.list)
  
  //Construct query finding contacts of user
  val userToSgroupQuery: Query[Column[Int], Int] = user_to_sgroup.sortBy(_.user_to_sgroup_id).map(_.user_to_sgroup_id)
  println(userToSgroupQuery.list)
  
  //Construct query finding contacts of user
  /*WORNG WAY
  for {
     g <- listof_events if g.sgroup_id === 1 //get the sgroup
     e <- g.events
  } yeild(e.reg_events_title)
  */
  

  val joinQuery: Query[(Column[String]), (String)] = for {
     g <- listof_events if g.sgroup_id === 1 //get the sgroup
     e <- g.events
  } yield(e.reg_events_title)
  
  println(joinQuery.list)

  */
  
  
  
  var valid = false	//is it a valid response?

  
  //Login Menu
  var signInOp = ""	//signIn option (signin or register)
  var currUser = ""	//the currently signed in user
  do {
	  println("=-=-= SuperMegaFunTime! =-=-=")
	  println("to exit program, type quit.")
	  println("1- signin")
	  println("2- new user")
	  signInOp = readLine()
	  if(signInOp == "1" || signInOp == "2") {
	    valid = true
	  }
	  else if(signInOp == "quit"){
		  println("goodbye!")
		  exit
	  }
	  else
	    println("\ninvalid option.")
  } while (!valid)
  

    
  //Signin Menu
  if (signInOp == "1") {	//user selected sign in
    valid = false
    do{
    	println("\n=-=-=-= SignIn =-=-=-=")
    	println("to exit program, type quit.")
    	println("enter username:")
    	var username = readLine();	//get username
    	if(username == "quit") {	//if user enters quit
    	  println("goodbye!")
		  exit
    	}
    	println("enter password:")
    	var pass = readLine()		//get password
    	
    	//query database
    	val userQuery: Query[Column [String], String] = reg_users.filter(_.user_name === username).filter(_.user_password === pass).map(_.user_name)
    	if (userQuery.exists.run) { 	//check if there was a match
    	  currUser = userQuery.first 	//save currently signed-in user
    	  println("welcome back " + currUser + "!")
    	  valid = true
    	}
    	else
    	  println("\nuser & password combination not found")
    } while (!valid)	//while there is no valid answer, ask again
  } //end user signIn
  
  
  //Registration Menu
  else if(signInOp == "2") {	//user selected register
	var username = ""
	var password = ""
	var email = ""
	  
    println("\n=-=-=-= SignUp =-=-=-=")
    println("to exit program, type quit.")
    
    //get username
    valid = false
    do {
	    println("enter a new username")
	    username = readLine().trim().toLowerCase()	//get username input
	    val userQuery: Query[Column [String], String] = reg_users.filter(_.user_name === username).map(_.user_name) //check if username exists in database
	    if(!userQuery.exists.run)					//if username doesnt exist,
	    	valid = true							//its valid
	    else if(username == "quit") {				//user types quit
	    	println("goodbye!")
	    	exit									//end program
	    }
	    else										//else username already exists
	    	println("\nusername " + username + " already exists")
    } while(!valid)	//if the username is not valid, ask again
      
    println("username: " + username)
    
    
    //get password
    var passMatch = false	//passwords match
    do {
    	//password input
    	valid = false
	    do {
	    	println("\nenter password")
	    	password = readLine()						//get password input
	    	if(password == "quit") {					//if user types quit
	    		println("goodbye!")
	    		exit									//end program
	    	}
	    	if(password.length() >= 3 && password.length() <= 16)	//if password is the correct length
	    		valid = true										
	    	else													//else invalid password
	    		println("password must be betwee 3 and 16 characters.")
	    } while(!valid)	//if the password is invalid, ask again
	    
	    //verify password
	    println("retype password")
	    val passRetype = readLine()		//get password again
	    if(password == passRetype)		//if equal to previous,
	    	passMatch = true			//its a match
	    else
	      println("passwords do not match.")    
    } while(!passMatch)	//if passwords dont match, ask again
      
      
    //get email
    valid = false
    do {
	    println("\nenter e-mail address")
	    email = readLine().trim()		//get email input
	    val userQuery: Query[Column [String], String] = reg_users.filter(_.user_email === email).map(_.user_name) //check if email already in database
	    if(!userQuery.exists.run)		//if not in database,
	    	valid = true				//it is a valid email
	    else if(email == "quit") {	//if user types quit
	    	println("goodbye!")
	    	exit						//exit program
	    }
	    else							//else email already in database
	    	println("\nemail " + email + " already has an account.")
    } while(!valid)	//if the username is not valid, ask again
      
    println("email: " + email)
      
    
    //reg_users += (1, username, password, email)	//add this user to the table
    												//ERROR: PRIMARY_KEY_VIOLATION
    currUser = username
    println("\nuser " + username + " created. (well not really. TODO: we need to figure out primary key autogen)")
    println("Welcome to SMFT!")
  } //end user registration
  
  
  //we now have a logged-in user
  val currUserID = reg_users.filter(_.user_name === currUser).first()._1 //grab the id for this useer
  
  
  //Main Menu
  var mainOp = ""
  valid = false
  do {
    println("\n=-=-=-= Main Menu =-=-=-=")
	println("to exit program, type exit.")
	println("1- Events")
	println("2- Friends")
	//println("3- My Group")
	println("4- Logout")
	
	mainOp = readLine()
	//exit
    if(mainOp == "exit") {		//if user typed exit
      println("\ngoodbye")
      exit
    }
    
    //events
    else if(mainOp == "1") {	//if user selected events
      do {
	      println("\n=-=-=-= Events =-=-=-=-=")
	      println("to exit program, type exit.")
	      println("0- back")
	      //Construct query finding events
	      val eventQuery = reg_events.sortBy(_.reg_events_id).map(_.reg_events_title)
	      eventQuery.foreach(println) //cant figure out how to print this in a better way.... 
	      val response = readLine()
	      if(response == "exit") {		//if user typed exit
	    	  println("\ngoodbye")
	    	  exit
	      }
	      else if(response == "0")		//user selected back
	          valid = true
	      else
	        println("invalid response")//else invalid response
      } while (!valid)
      valid = false
    } //end events
    
    
    //friends
    else if(mainOp == "2") {		//if user selected Friends
      do {
	      println("\n=-=-=-= Friends =-=-=-=")
	      println("to exit program, type exit.")
	      println("0- back")
	      //construct query finding my friends
	      val friendQuery: Query[(Column[String]), (String)] = for {
	        c <- contacts if c.contacts_owner_id === 1
	        o <- c.contact
	      } yield(o.user_name)
	      friendQuery.foreach(println)
      
	      val response = readLine()
		  if(response == "exit") {		//if user typed exit
		   	  println("\ngoodbye")
		   	  exit
		  }
		  else if(response == "0")		//user selected back
		      valid = true
		  else
		      println("invalid response")//else invalid response
	  	  } while (!valid)
	      valid = false
    }
    
    //logout
    else if (mainOp == "4"){
      println("\nlogging out")
      exit
    }
    
    //other
    else
      println("invalid option")
  } while (!valid)
  
		  
  
  
  
  println("DONE")   
  } //end session
} //end modSlick