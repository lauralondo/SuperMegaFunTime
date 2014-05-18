/**
 * SuperMegaFunTime
 * Paul Nguyen, Molly Strasser, Laura Londo
 * 15 May 2014
 * Programming Languages Practicum 2
 * 
 * This is a Scala program implementing a social network application using the Slick database library.
 */

import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ProvenShape, ForeignKeyQuery}

object SMFT extends App {

  //val reg_users: TableQuery[reg_users]
  //ERROR:only classes can have declared but undefined members
  
  //The query interface for the * table
  //these are used to make queries to the database
  val user_query        = TableQuery[user_table]
  val event_query       = TableQuery[event_table]
  val sgroup_query      = TableQuery[sgroup_table]
  val contact_query     = TableQuery[contact_table]
  val sgroupMem_query   = TableQuery[sgroupMem_table]
  val eventList_query   = TableQuery[eventList_table]
  val sgroupInv_query   = TableQuery[sgroupInv_table]
  val contactInv_query  = TableQuery[contactInv_table]
  val vote_query        = TableQuery[vote_table]
  
  //Create a connection (called a "session") to an in-memory H2 database
  Database.forURL("jdbc:h2:mem:hello", driver = "org.h2.Driver").withSession { implicit session =>
  
  //Create the schema by combining the DDLs for the tables using the query interfaces
  //this is basically building the dictionary of operations available to perform on the tables 
  (user_query.ddl ++ event_query.ddl ++ sgroup_query.ddl ++ contact_query.ddl ++ 
  sgroupMem_query.ddl ++ eventList_query.ddl ++ sgroupInv_query.ddl ++ contactInv_query.ddl ++ vote_query.ddl).create

  
  
  

  //////////////////////////////// Insert some starter data into the database //////////////////////////////////////
  
  //insert users (username, password, e-mail)
  val user_insert: Option[Int] = user_query ++= Seq(
      user_cc("paul", "Forte_1234", "paulnguyen@sandiego.edu"),
      user_cc("laura", "123", "123@123.com"),
      user_cc("molly", "123", "123@123.com"),
      user_cc("jasper", "123", "123@123.com"),
      user_cc("sat", "123", "123@123.com")
      )
  
  //insert events (title, pitch, description, street, city, state, zipcode, day, time
  val event_insert: Option[Int] = event_query ++= Seq(
     event_cc("TacoBell","Come party with TacoBell","TacoBell is launching their new signature 5 cheese taco. Come to our block party in downtown","123 5th","San Diego","CA","92101", 0,21,500,3),
     event_cc("Board Games","Play D&D with a master","Local table top game masters are gathering at USD to teach students how to play new board games","5998 Alcal� Park","San Diego","CA","92110", 0,21,10,7),
     event_cc("Star Trek Meetup","Pre-game the Con with fellow Trekkies","Trekkies unite at Marriott in downtown. Cosplay appreciation, drinking, and battle plans for the Con!","660 K St","San Diego","CA","92101", 0,21,500,5),
     event_cc("Comic Con 2014","Comic Con International in San Diego","Comic Con International is taking over downtown San Diego. Come be part of the world entertainment yearly kickoff in the Convention Center","111 W Harbor Dr","San Diego","CA","92101", 1,21,1000,100),
     event_cc("Padres","San Diego Padres vs Los Angeles Dodgers","Come out to the ball game and support your team","100 Park Blvd","San Diego","CA","92101", 2,21,500,120)
     )
     
  //insert social groups (group creator's id, group name)
  val sgroup_insert: Option[Int] = sgroup_query ++= Seq(
      sgroup_cc(1, "Paul's Group"),
      sgroup_cc(2, "Laura's Group")
      )
  
  //insert contact references (owner's user id, contact's user id)
  val contact_insert: Option[Int] = contact_query ++= Seq(
      contact_cc(1,2),
      contact_cc(2,1),
      contact_cc(2,3),
      contact_cc(3,2),
      contact_cc(3,4),
      contact_cc(4,3),
      contact_cc(4,1),
      contact_cc(1,4),
      contact_cc(2,4),
      contact_cc(4,2),
      contact_cc(5,2),
      contact_cc(2,5),
      contact_cc(1,5),
      contact_cc(5,1)
      )
      
  //insert social group member references (sgroup id, user id)
  val sgroupMem_insert: Option[Int] = sgroupMem_query ++= Seq(
      sgroupMem_cc(1,1),
      sgroupMem_cc(1,3),
      sgroupMem_cc(1,5),
      sgroupMem_cc(2,2),
      sgroupMem_cc(2,4)
      )
      
  //insert event-to-group references (sgroup id, event id, number of local up-votes)
  val eventList_insert: Option[Int]= eventList_query ++= Seq(
      eventList_cc(1,1,0),
      eventList_cc(1,4,0),
      eventList_cc(2,3,0),
      eventList_cc(2,4,0),
      eventList_cc(2,5,0)
      )
  
  //insert some friend invitations (sender user id, recipient user id)
  val contactInv_insert: Option[Int]= contactInv_query ++= Seq(
      contactInv_cc(1,3),
      contactInv_cc(5,3)
      )
  
  //test print full table queries
  // println(user_query.list)
  // println(event_query.list)
  // println(sgroup_query.list)
  // println(contact_query.list)
  // println(sgroupMem_query.list)
  // println(eventList_query.list)
  
  
  /////////////////////////////// Test Queries ////////////////////////////////////////

  //val composedQuery
  //val namesQuery: Query[Column [String], String] = user_cc.sortBy(_.user_name).map(_.user_name)
  // val namesQuery = user_query.sortBy(_.user_name).map(_.user_name)
  // println(namesQuery.list)
  
  //Construct query finding events
  //val eventQuery: Query[Column[String], String] = reg_events.sortBy(_.reg_events_id).map(_.reg_events_title)
  // val eventQuery = event_query.sortBy(_.event_id).map(_.event_title)
  // println(eventQuery.list)
  
  //Construct query finding sgroup
  //val sgroupQuery: Query[Column[Int], Int] = sgroup.sortBy(_.sgroup_id).map(_.sgroup_lead)
  // val sgroupQuery = sgroup_query.sortBy(_.sgroup_id).map(_.sgroup_lead)
  // println(sgroupQuery.list)
  
  //Construct query finding contacts of user
  //val contactsQuery: Query[Column[Int], Int] = contacts.filter(_.contacts_owner_id === 2).map(_.contactto_owner_id)
  // val contactsQuery = contact_query.filter(_.contact_owner_id === 2).map(_.contactto_owner_id)
  // println(contactsQuery.list)
  
  //Construct query finding contacts of user
  //val userToSgroupQuery: Query[Column[Int], Int] = user_to_sgroup.sortBy(_.user_to_sgroup_id).map(_.user_to_sgroup_id)
  // val userToSgroupQuery = sgroupMem_query.sortBy(_.sgroup_id).map(_.sgroup_id)
  // println(userToSgroupQuery.list)
  
  //val joinQuery: Query[(Column[String]), (String)] = for {
  //   g <- listof_events if g.sgroup_id === 1 //get the sgroup
  //   e <- g.events
  //} yield(e.reg_events_title)
  
  // val joinQuery = for{
  //     g <- eventList_query if g.sgroup_id === 1
  //     e <- g.event
  // } yield(e.event_title)
  // 
  // println(joinQuery.list)
  
  
  
  
  ////////////////////////////////////////////////// User Interface ////////////////////////////////////////////////
    var currUserID = 0 //the current user (not yet logged in)
  
    
    
    //login function
    //prompts the user to enter their username and password to log in to the system
    // void -> string
    def login() : String =  {
        var valid = false 
        var currUser = "" //current user's name
	    do{
	    	println("\n=-=-=-= SignIn =-=-=-=")
	    	println("to exit program, type exit.")
	    	println("enter 0 to go back")
	    	println("enter username:")
	    	var username = readLine();	//get username
	    	if(username == "exit") {	//if user enters quit, exit the program
	    	  println("goodbye!")
			  exit
	    	}
	    	if(username == "0"){        //user selected "back"
	    	    valid = true            //set valid to true to end the login menu
	    	}
	    	if(valid == false){
		    	println("enter password:")
		    	var pass = readLine()		//get password
		    	
		    	//query database
		    	val userQuery = user_query.filter(_.user_name === username).filter(_.user_password === pass).map(_.user_name)
		    	if (userQuery.exists.run) { 	//check if there was a match
		    	  currUser = userQuery.first 	//save currently signed-in user
		    	  println("welcome back " + currUser + "!")
		    	  valid = true       //set valid to true to end the login menu
		    	}
		    	else{ //else there was no username-password match in the database
		    	  println("\nuser & password combination not found")
		    	}
	    	}
	    } while (!valid)	//while there is no valid answer, ask again
	    return currUser     //return the current user's name
   }

    
  
  
    
    //registration method
    //prompts the user to enter their information to create a new user account in the database
    // void -> string
    def register() : String = {
    	var username = ""
		var password = ""
		var email = ""		  
	    println("\n=-=-=-= SignUp =-=-=-=")
	    println("to exit program, type exit.")
	    println("0- back")
	    
        var back = false //go back out of this menu
                
        //get username
	    var valid = false
        do {
		    println("enter a new username")
		    username = readLine().trim().toLowerCase()	//get username input
		    val userQuery = user_query.filter(_.user_name === username).map(_.user_name) //check if username exists in database
		    
		    if(username == "exit") {   //user types quit
		    	println("goodbye!")
		    	exit				   //end program
		    }
		    else if(username == "0"){  //back to the previous menu
		      println("back") 
		      return "" //no user was selected
		    }
		    else if(username.length() < 5){
		      println("Username must be larger than 4 characters")
		    }
		    else if(!userQuery.exists.run)				//if username doesn't exist,
		    	valid = true							//it's valid
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
		    	password = readLine()			//get password input
		    	if(password == "exit") {		//if user types quit
		    		println("goodbye!")
		    		exit						//end program
		    	}
		    	else if(password == "0"){       //user selected back 
		    		println("back")
		    		return ""
		    	}
		    	if(password.length() >= 3 && password.length() <= 16)	//if password is the correct length
		    		valid = true										
		    	else													//else invalid password
		    		println("password must be betwee 3 and 16 characters.")
		    } while(!valid)	//if the password is invalid, ask again
		    
		    //verify password
		    println("retype password")
		    val passRetype = readLine()		//get password again
		    if(password == passRetype) {	//if equal to previous,
		    	passMatch = true			//it's a match
		    }
		    else {
		      println("passwords do not match.")  
		    }
	    } while(!passMatch)	//if passwords don't match, ask again
	          
	    //get email
	    valid = false
	    do {
		    println("\nenter e-mail address")
		    email = readLine().trim()		//get email input
		    val userQuery = user_query.filter(_.user_email === email).map(_.user_name) //check if email already in database
		    if(email == "0"){ 				//if the user selected back
		      println("back")
		      return ""
		    }
		    else if(email.length() < 5  ||  !email.contains("@")) {   ///email must be at least 5 characters and have an @
		    	println("invalid email")
		    }
		    else if(!userQuery.exists.run){	//if not in database,
		    	valid = true				//it is a valid email
		    }
		    else if(email == "exit") {		//if user types quit
		    	println("goodbye!")
		    	exit						//exit program
		    }
		    else							//else email already in database
		    	println("\nemail " + email + " already has an account.")
	    } while(!valid)	//if the username is not valid, ask again
	    println("email: " + email)
	      
	    user_query ++= Seq( user_cc(username, password, email))	//add this user to the table
	    println("\nuser " + username + " created.")
	    println("Welcome to SMFT!")
	    return username
    } //end register method
    
    
    
    
    
    //function tests whether the given string is a number
    // string -> boolean
    def isAnInt(str: String): Boolean = {
    	try { //try to parse to int
    	  str.toInt
    	  return true
    	}
    	catch { //if it fails, return false -- it's not an int
    	  case e: Exception => false
    	}
    } //end isNumeric
    
    
    
    
    
    //the event page view
    //displays all the information about the event with the given ID
    //can be viewed globally or locally (relative to the user's group)
    // int * boolean -> void
    def eventView( eventId:Int, globOrLoc:Boolean ) : Unit = {
    	val eventQuery = event_query.filter(_.event_id === eventId) //search for the event
    	
    	if (!eventQuery.exists.run) { //if the event does not exist, print & return
			println("There is no such event...")
			return
    	}
    	else { //else the event does exist
    		
    		var valid = false
	    	do{
	    		val event = eventQuery.first()   //get the event
	    		var myGroupId = -1               //id of the current user's group
	    		
	    		//find the current user's group
	    		val groupQuery = sgroupMem_query.filter(_.member_id === currUserID) //search for the current user's group
	    		if (groupQuery.exists.run) {                //if the current user is in a group,
	    		  myGroupId = groupQuery.first().sgroup_id  //find group id
	    		}
	    		
	    		//search for the event to group relation
	    		val groupRelation = eventList_query.filter(_.event_id === eventId).filter(_.sgroup_id === myGroupId)
	    		var groupRel : eventList_cc = new eventList_cc(0,0,0)
	    		
	    		println("\n=-=-=-= " + event.event_title + " =-=-=-=") 					//name
	    		println(event.event_description) 										//description
	    		println("location: " + event.event_street + " " + event.event_city + " " + event.event_state + " " + event.event_zip) //place
	    		println("time: " + event.event_time + " day: " + event.event_day) 		//time
	    		
	    		//print out vote count
	    		if (!globOrLoc) { //if we're in global view
	    			println("votes: " + event.event_up)		      //print global votes
	    		}
	    		else {  //if we're in group view
	    		    groupRel = groupRelation.first();             //get the current user's event-to-group relation
	    			println("group votes: " + groupRel.vote_up)   //print local votes
	    		}

	    		println("to exit program, type exit.")
	    		println("0- back")
	    		println("1- upvote")
	    		println("2- downvote")
	    		if (myGroupId > -1) {                  //if the current user is in a group
	    		  var eventInGroupQuery = eventList_query.filter(_.event_id === event.event_id).filter(_.sgroup_id === myGroupId)
	    		  if (!eventInGroupQuery.exists.run) { //if the event is not already in the group
	    			  println("3- add to my group")
	    		  }
	    		}
	    	
	    		var globVote = event_query.filter(_.event_id === eventId) //get the event in order to get global votes
	    		var response = readLine().trim() //get response
	    		if( response == "exit") { //exit
	    		  println("Goodbye!")
	    		  exit
	    		}
	    		else if (response == "0") { //back
	    		  valid = true
	    		}
	    		else if(response == "1") { //up-vote globally 
	    		  println("Event has been up-voted.")
	    		  var voteUp = for { //get the event up-vote column
	    			  c <- event_query if c.event_id === eventId 
	    		  } yield c.event_up
	    		  val temp = event_query.filter(_.event_id === eventId).map(_.event_up).first() + 1 //add one to the current vote count
	    		  voteUp.update(temp) //update the event with the new vote count
	    		   
	    		  if(globOrLoc) { //if local view
		    		  voteUp = for { //get the event up-vote column
		    			  c <- eventList_query if c.event_id === eventId && c.sgroup_id === myGroupId
		    		  } yield c.vote_up
		    		  val temp = eventList_query.filter(_.event_id === eventId).filter(_.sgroup_id === myGroupId).map(_.vote_up).first() + 1 //add to local count
		    		  voteUp.update(temp) //update local up-votes  
	    		  }
	    		} //end up-vote event
	    		
	    		else if( response == "2") { //down-vote event
	    			println("Event has been down-voted.")
	    			var voteDown = for {
		    			c <- event_query if c.event_id === eventId 
		    		} yield c.event_up //get global up vote column
		    		val temp = event_query.filter(_.event_id === eventId).map(_.event_up).first() - 1 //subtract from count
		    		voteDown.update(temp) //update votes
		    		
		    		if(globOrLoc) { //if local
		    		  voteDown = for {
		    			  c <- eventList_query if c.event_id === eventId && c.sgroup_id === myGroupId
		    		  } yield c.vote_up //get local up-vote column
		    		  val temp = eventList_query.filter(_.event_id === eventId).filter(_.sgroup_id === myGroupId).map(_.vote_up).first() - 1 //subtract from votes
		    		  voteDown.update(temp) //update votes
	    		  }
	    		} //end down-vote event
	    		
	    		else if( response == "3"  &&  myGroupId != -1) { //add event to current user's group
	    			val eventQuery = eventList_query.filter(_.sgroup_id === myGroupId).filter(_.event_id === eventId) //search for this event-to-group relation
	    			if(eventQuery.exists.run) { //if the relation is found, the event is already in this group
	    				println("this event is already in your group.")
	    			}
	    			else { //else the event is not in the group
	    				eventList_query ++= Seq( eventList_cc(myGroupId, eventId,0)) //add the event-to-group relation
	    				println("Event added to your group!")
	    			}
	    		} //end add event to group
	    		else { 	//else, garbage response
	    		  println("invalid response")
	    		}
	    	  
	    	}while(!valid);
    	} //end else
    } //end eventView
    
    
    
    
    
    
    
    def userView(userId : Int) : Unit = {
      var valid = false
      do {
        val userQuery = user_query.filter(_.user_id === userId)     		    //search for the viewed user
        if(userQuery.exists.run) { //if the viewed user exists
        	val groupQuery = sgroupMem_query.filter(_.member_id === currUserID) //search for the current user's group
        	val user = userQuery.first()                                        //get the viewed user
        	println("\n=-=-=-= " + user.user_name + "=-=-=-=")
		    println("type exit to exit")
		    println("0- back")
		    
		    //print friend request or remove options
		    var contactRemOrAdd = -1
		    //find if the current user know the viewed user
        	val contactQuery = contact_query.filter(_.contact_owner_id === currUserID).filter(_.contactto_owner_id === user.user_id) 
	        if (contactQuery.exists.run) {        //if the viewed user is a contact with the current user
	          println("1- remove friend")
	          contactRemOrAdd = 0  //remove a contact
	        }
	        else { //else the current user does not know the viewed user
	          println("1- send friend request")
	          contactRemOrAdd = 1  //adding a contact
	        }
	    
        	//print group invite or remove options (only if currUser is in a group)
        	var groupRemOrAdd = -1 //boolean whether we can remove or add the viewed user to the group
	        if(groupQuery.exists.run) { 	   //if current user is in a group
	        	val currGroupId = groupQuery.first().sgroup_id       //get the current user's group id      
		        val inGroupQuery =  sgroupMem_query.filter(_.member_id === user.user_id)        //find the viewed user's group
		        //if the viewed user is in a group and is in the same group as the current user
		        if (inGroupQuery.exists.run && inGroupQuery.first().sgroup_id == currGroupId) { 
		        	println("2- remove from group")
		            groupRemOrAdd = 0        //we will have the option to remove the viewed user from the group
		        }
	        	//else the viewed user is not in the current user's group & has not been invited
		        else if(!sgroupInv_query.filter(_.recip_id === userId).filter(_.sgroup_id === currGroupId).exists.run) { 
		        	println("2- invite to group")
		            groupRemOrAdd = 1         //we will have the option to invite the viewed user to the group
		        }     
	        } //end if the current user is in a group
	      
        	//user response
        	var response = readLine() //get user input
        	 
        	if (response == "exit") { //exit
        	  println("goodbye!")
        	  exit
        	}
        	else if(response == "0") { //go back
        	  valid = true
        	}
        	
        	else if(response == "1") {   //remove or add to contacts
        	  if(contactRemOrAdd == 0) { //remove from contacts
        	    contactQuery.delete //delete contact relation from current user to other
        	    //delete contact relation in other direction as well
        	    contact_query.filter(_.contact_owner_id === user.user_id).filter(_.contactto_owner_id === currUserID).delete
        	    println("You are no longer friends with " + user.user_name + ". XD")
        	  }
        	  else if (contactRemOrAdd == 1) { //invite to contacts
        		  contactInv_query ++= Seq( contactInv_cc(currUserID, userId)) //create a new friend invitation
        		  println("Friend Request Sent")
        	  }
        	} //end remove or add to contacts
        	
        	else if(response == "2") { //remove or add user to group
        		val inGroupQuery =  sgroupMem_query.filter(_.member_id === user.user_id)   //find the viewed user's group relation
        		if(groupRemOrAdd == 0) { //remove from group
        			inGroupQuery.delete //delete group relation
        			println("User deleted from group.")
        		}
        		else { //add to group
        			val currGroupId = groupQuery.first().sgroup_id       //get the current user's group id      
        			sgroupInv_query ++= Seq( sgroupInv_cc(currGroupId, currUserID, userId)) //create new user-to-group relation 
					println("Group invite Sent")
        		}
        	} //end remove or add to group
        	
        	else {
        	  println("invalid response")
        	}	
        } //end if the viewed user exists
        
        else { //else the viewed user does not exist
          println("ERROR: User# " + userId + " does not exist.")
        }
      } while (!valid) //repeat menu if not valid response
    } //end user view 
    
    
    
    //view the friend invitation with the given id number
    // int -> void
    def friendInvite(inviteId : Int) : Unit = {
		var valid = false
		do {
			  val invite = contactInv_query.filter(_.invite_id === inviteId).first() //get the invitation
			  
			  println("\n=-=-=-= Friend Invite =-=-=-=")
			  println("Type exit to exit.")
			  println("0- back")
			  println("1- accept invitation")
			  println("2- delete invitation")
			  
			  var response = readLine().trim() //get user response
			  
			  if(response == "exit") { //exit
				    println("goodbye!")
				    exit
			  }
			  
			  else if (response == "0") { //go back
				  valid = true
			  }
			  
			  else if(response == "1") { //accept invite
				   val contact_insert: Option[Int] = contact_query ++= Seq(
				    	contact_cc(invite.sender_id, invite.reciep_id), //add relation from sender to recipient
				    	contact_cc(invite.reciep_id, invite.sender_id)  //and add relation in other direction
					)
					
					val senderName = user_query.filter(_.user_id === invite.sender_id).map(_.user_name).first() //get sender's name
					val invite2 = contactInv_query.filter(_.invite_id === inviteId).delete //delete the invitation
				    println(senderName + " is now your friend!")
				    valid = true
			  }
			  
			  else if (response == "2") { //delete invite (reject invitation)
				    val invite2 = contactInv_query.filter(_.invite_id === inviteId).delete //delete invite
				    println("Invite deleted.") 
				    valid = true
			  }
			  else { //else invalid response
				  println("Invalid response.")
			  }
      }while (!valid)
    } //end friend invite
    
    
    
    //view the group invitation with the given id
    // int -> void
    def groupInvite(inviteId : Int) : Unit = {
    		var valid = false
    		do {
    		  val invite = sgroupInv_query.filter(_.invite_id === inviteId).first() //get the invitation
    		  
    		  println("\n=-=-=-= Group Invite =-=-=-=")
    		  println("Type exit to exit.")
    		  println("0- back")
    		  println("1- accept invitation")
    		  println("2- delete invitation")
    		  
    		  var response = readLine().trim() //get user response
    		  
    		  if(response == "exit") { //exit program
    		    println("goodbye!")
    		    exit
    		  }
    		  
    		  else if (response == "0") { //go back
    		    println("back")
    		    valid = true
    		  }
    		  
    		  else if(response == "1") { //accept invite
    		   val sgroup_insert: Option[Int] = sgroupMem_query ++= Seq(
    		    	sgroupMem_cc(invite.sgroup_id, invite.recip_id) //add user-to-group relation
    			)
    			
    			val senderName = sgroup_query.filter(_.sgroup_id === invite.sgroup_id).map(_.sgroup_name).first() //get the group name
    			val invite2 = sgroupInv_query.filter(_.invite_id === inviteId).delete //delete the invitation
    		    println("You are now a member of " + senderName + ".")
    		    valid = true
    		  }
    		  
    		  else if (response == "2") { //delete invite (reject)
    		    val invite2 = sgroupInv_query.filter(_.invite_id === inviteId).delete //delete the invitation
    		    println("Invite deleted.") 
    		    valid = true
    		  }
    		  
    		  else { //else invalid response
    		    println("Invalid response.")
    		  }
      }while (!valid) //repeat menu if not valid
    } //end group invite
    
    
    
    
 
      var valid = false    //is it a valid response?
      //start main menu
	  do {
		  valid = false    //is it a valid response?

		  //Login Menu
		  var currGroupID = -1;
		  var signInOp = ""	//signIn option (signin or register)
		  var currUser = ""	//the currently signed in user
		  //var currUserID = 0
		  do {
		    do{
		      currUser = ""
		      currGroupID = -1
			  println("\n=-=-= SuperMegaFunTime! =-=-=")
			  println("to exit program, type exit.")
			  println("1- signin")
			  println("2- new user")
			  signInOp = readLine()
			  
			  if(signInOp == "1" || signInOp == "2") {
				  //Signin Menu
				  if (signInOp == "1") {		//user selected sign in
				    
	                currUser = login()
				    valid = true
				  } //end user signIn
				  
				  //Registration Menu
				  else if(signInOp == "2") {	//user selected register
					currUser = register()
					valid = true
				  } //end user registration
			  }
			  else if(signInOp == "exit"){ 		//signin option quit
				  println("goodbye!")
				  exit
			  }
			  else								//invalid sign in option
			    println("\ninvalid option.")
		  } while(currUser == "")
		    	
		      
	      
	      
	      
	      
			  //Main Menu
			  var mainOp = ""
			  valid = false
			  do {
			  //we now have a logged-in user
			  currUserID = user_query.filter(_.user_name === currUser).map(_.user_id).first() //search for the id for this user
			  var something = sgroupMem_query.filter(_.member_id === currUserID).map(_.sgroup_id)
			  if(something.exists.run){ //if the user exists
			    currGroupID = something.first() //grab the current group id id
			  }
			  else{
			    currGroupID = -1 //else not in a group
			  }
			    println("\n=-=-=-= Main Menu =-=-=-=")
				println("to exit program, type exit.")
				println("0- Logout")
				println("1- Events")
				println("2- Friends")
				if(currGroupID == -1){ //if the user is not in a group, give option to create a group
				  println("3- Create A Group")
				}
				else{ //else the option to view their group
				  println("3- My Group") 
				}
			    
				println("4- Create Event")
				var friendinvite = contactInv_query.filter(_.recip_id === currUserID) //search for contact invites
			    var groupinvite = sgroupInv_query.filter(_.recip_id === currUserID) //search for group invites
				if(friendinvite.exists.run || groupinvite.exists.run){ //if there are invitations, show invite menu
					println("5- You've Got Mail") 
			    }
			    
				mainOp = readLine() //get user response
				//exit
			    if(mainOp == "exit") {		//if user typed exit
			      println("\ngoodbye")
			      exit
			    }
			    
			    //events
			    else if(mainOp == "1") {	//if user selected events menu
			      do {
				      println("\n=-=-=-= Events =-=-=-=-=")
				      println("to exit program, type exit.")
				      println("0- back")
				      
				      //Construct query finding events
				      val eventQuery = event_query.sortBy(_.event_up.desc) //all events sorted by up-vote
				      
				      //print event list menu
				      var idArray = new Array[Int](eventQuery.length.run + 1) //create an array to store the event IDs
				      var index = 1 				//index in the printout list and array
				      for (event <- eventQuery) { 	//for every event,
				    	  println(index + "- " + event.event_up + " " +  event.event_title + ": " + event.event_pitch) //print event info
				    	  idArray(index) = event.event_id.get //add this event ID to the array
				    	  index += 1 //increment index
				      } //end for each event
				      
				      
				      val response = readLine().trim() //get response
				      if(response == "exit") {		   //if user typed exit
				    	  println("\ngoodbye")
				    	  exit
				      }
				      else if(response == "0") {	   //user selected back
				        println("back")
				          valid = true
				      }
				      else if (isAnInt(response) && response.toInt <= eventQuery.length.run) { //response is an event number
				    	  eventView(idArray(response.toInt), false) //show the event view for this event globally
				        
				      }
				 
				      else {
				        println("invalid response")//else invalid response
				      }
			      } while (!valid)
			      valid = false
			    } //end events view
			    
			    
			    //friends view
			    else if(mainOp == "2") { //if user selected Friends
			      do {
				      println("\n=-=-=-= Friends =-=-=-=")
				      println("to exit program, type exit.")
				      println("0- back")
				      println("1- Add A Friend")
				      //construct query finding user's friends
				      val friendQuery = for {
				        c <- contact_query if c.contact_owner_id === currUserID
				        o <- c.contact
				      } yield(o)
   
				      //Construct query finding events
				      var idArray = new Array[Int](friendQuery.length.run + 2) //create an array to store the event IDs
				      var index = 2 				//index in the printout list and array
				      for (friend <- friendQuery) { 	//for every event,
				    	  println(index + "- " + friend.user_name)//event.event_up + " " +  event.event_title + ": " + event.event_pitch) //print event info
				    	  idArray(index) = friend.user_id.get//.event_id.get //add this event ID to the array
				    	  index += 1 //increment index
				      } //end for each event

				      val response = readLine()  //get user response
					  if(response == "exit") {   //if user typed exit
					   	  println("\ngoodbye")
					   	  exit
					  }
					  else if(response == "0"){	 //user selected back
					      valid = true
					  }
					  else if(response == "1"){  //add a friend
						  do{
							var friendusername = "" 
							println("\n=-=-=-= Add a Friend =-=-=-=")
							println("type exit to exit the program")
							println("0- back")
							println("Type in the username of the friend you wish to add.")
							friendusername = readLine().trim() //get user response
							var friendId = user_query.filter(_.user_name === friendusername).map(_.user_id) //get friend's id
							if (friendusername == "exit"){ //exit
							  println("goodbye")
							  exit
							}
							else if (friendusername == "0"){ //back
							  println("back")
							  valid = true
							}
							else if(friendId.exists.run){    //if this person exists
							  //search for contact
							  val contactSearch = contact_query.filter(_.contact_owner_id === currUserID).filter(_.contactto_owner_id === friendId.first())
							  if(contactSearch.exists.run) { //if you are already friends
							    println("You are already friends with " + friendusername)
							  }
							  else { //else the users are not yet friends
							    //search for an invite to this person from the current user
							    val requestSearch = contactInv_query.filter(_.sender_id === currUserID).filter(_.recip_id === friendId.first())
							    if(requestSearch.exists.run) { //if there is already an invite,
							      println("You have already sent " + friendusername + " a friend request.")
							    }
							    else { //else there is no invite
								  contactInv_query ++= Seq( contactInv_cc(currUserID, friendId.first())) //create a new friend invitation
								  println("Friend Request Sent")
								  valid = true
							    }
							  } //end else the users are not yet friends
							} //end this person exists
							
							else{ //else this person does not exist
							  println("User does not exist")
							}
						  } while(!valid) //repeat menu if not valid
						 valid = false
					  }
				      else if (isAnInt(response) && response.toInt <= friendQuery.length.run + 1) { //response is an event number
				    	  userView(idArray(response.toInt)) //get the selected event id
				      }
					
					  else { //else invalid response
					      println("invalid response")
					  }
				  } while (!valid) //repeat menu if not valid
				  valid = false //we do not want to exit the previous menu on return
			    } //end if friends menu
				
				
			    
			    //My group
		        else if(mainOp == "3") {  //if user selected My Group
		          if(currGroupID == -1){  //not in a group, show create group menu
		            var valid = false
		            
		            //get group name
		            do{
			            var groupname = ""
			            println("\n=-=-=-= Create A Group =-=-=-=")
			            println("to exit program, type exit.")
			            println("0- back")
			            
			            println("Enter the Group Name. Length 3-20")
			            groupname = readLine().trim() //get group name
			            if(groupname == "exit"){ //exit
			              println("Goodbye")
			              exit
			            }
			            else if(groupname == "0"){ //back
			              println("back")
			              valid = true
			            }
			            
			            else if(groupname.length() >= 3 && groupname.length() < 21){ //valid group name
			              valid = true
			              sgroup_query ++= Seq( sgroup_cc (currUserID, groupname)) //create group
			              currGroupID = sgroup_query.filter(_.sgroup_lead === currUserID).map(_.sgroup_id).first() //save group id
			              sgroupMem_query ++= Seq( sgroupMem_cc(currGroupID, currUserID)) //add this user as a member of the new group
			            }
			            else{ //group name too short
			              println("Group Name Must Be 3-20 Characters")
			            }
		            }while(valid == false) //repeat if invalid group name
		          }
		          
		          //else the user is in a group. show group menu
		          else{
			          do {
			        	  //search for current group id
					      val sgroupQuery = sgroupMem_query.filter(_.member_id === currUserID).map(_.sgroup_id)
					      val sgroup_id = sgroupQuery.first() //get the id
					      
					      val nameQuery = sgroup_query.filter(_.sgroup_id === sgroup_id).map(_.sgroup_name) //search for sgroup name
					      val sgroup_name = nameQuery.first() //get name
					      
					      println("\n=-=-=-= " + sgroup_name + " =-=-=-=")
					      println("to exit program, type exit.")
					      println("0- back")
					      println("1- members")
					      println("2- events")
					      
					      val response = readLine()  //get user input
						  if(response == "exit") {   //if user typed exit
						   	  println("\ngoodbye")
						   	  exit
						  }
						  else if(response == "0") { //user selected back
							  println("back")
						      valid = true
						  }
					      else if(response == "1") { //members
							  do {
								  println("=-=-=-= Members =-=-=-=")
								  println("0- back")
								  val memberQuery: Query[(Column[String]), (String)] = for {
									  c <- sgroupMem_query if c.sgroup_id === sgroup_id
									  o <- c.member
								  } yield(o.user_name) //yield joined query for members of this group
								  memberQuery.foreach(println) //print each member's username
								  
								  var response = readLine().trim() //get user response
								  if (response == "0") { //back
								    println("back")
								    valid = true
								  }
								  else { //else invalid response
								    println("invalid response")
								  }
							  } while (!valid) //repeat if not valid
							  valid = false
						  }
						  else if (response == "2") { //group events
						     do {
							      println("\n=-=-=-= Group Events =-=-=-=-=")
							      println("to exit program, type exit.")
							      println("0- back")
							      //search for all group events sorted by up-votes
							      val eventToGroup = eventList_query.filter(_.sgroup_id === sgroup_id).sortBy(_.vote_up.desc)
			
							      //print event list menu
							      var idArray = new Array[Int](eventToGroup.length.run + 1) //create an array to store the event IDs
							      var index = 1 				//index in the printout list and array
							      
							      for (relation <- eventToGroup) { 	//for every event,
							    	  val event = event_query.filter(_.event_id === relation.event_id).first()
							    	  println(index + "- " + relation.vote_up + " " +  event.event_title + ": " + event.event_pitch) //print event info
							    	  idArray(index) = event.event_id.get //add this event ID to the array
							    	  index += 1 //increment index
							      } //end for each event
							      
							      
							      val response = readLine().trim() //get response
							      if(response == "exit") {		   //if user typed exit
							    	  println("\ngoodbye")
							    	  exit
							      }
							      else if(response == "0") {	   //user selected back
							    	  println("back")
							          valid = true
							      }
							      else if (isAnInt(response) && response.toInt <= eventToGroup.length.run) { //response is an event number
							    	  eventView(idArray(response.toInt), true) //start event view function for this event   
							      }
							 
							      else { //else invalid response
							        println("invalid response")
							      }
						      } while (!valid) //repeat menu if not valid
						      valid = false
						  }
					      
	 
						  else{
						    println("invalid response") //else invalid response
						  }
				  	  } while (!valid) //repeat menu if not valid
		          } //end group menu
			      valid = false
		        } //end view or create group
			    
			    
			    
			    //Create Event
		        else if (mainOp == "4") {
				    do{
				    	println("\n=-=-=-= Create Event =-=-=-=")
				    	println("to exit program, type exit.")
				    	println("enter event name:")
				    	var eventname = readLine();	//get group number
				    	if(eventname == "exit") {	//if user enters quit
				    	  println("goodbye!")
						  exit
				    	}
				    	println("enter event pitch:")
				    	var eventPitch = readLine();	//get group number
				    	if(eventPitch == "exit") {	//if user enters quit
				    	  println("goodbye!")
						  exit
				    	}
				    	println("Please add description of event")
				    	var eventDescription = readLine(); //get description of event
				    	if(eventDescription == "quit"){ //if user enters quit
				    		println("goodbye")
				    		exit
				    	}
				    	println("Please add name of street for event")
				    	var eventStreet = readLine(); //get street name of event
				    	if(eventStreet == "quit"){ //if user enters quit
				    		println("goodbye")
				    		exit
				    	}    	
				     	println("Please add name of city for event")
				    	var eventCity = readLine();  //get city of event
				    	if(eventCity == "quit"){ //if user enters quit
				    		println("goodbye")
				    		exit
				    	} 
				    	println("Please add state of event")
				    	var eventState = readLine(); //get state of event
				    	if(eventState == "quit"){ //if user enters quit
				    		println("goodbye")
				    		exit
				    	}    	
				    	println("Please add zip code of event")
				    	var eventZipCode = readLine(); //get zip code of event
				    	if(eventZipCode == "quit"){ //if user enters quit
				    		println("goodbye")
				    		exit
				    	}
				    	println("Please add day of event in integer form")
				    	var eventDate: String = readLine(); //get day of event
				    	var eventDateInt: Int = eventDate.toInt;
				    	if(eventDate == "quit"){ //if user enters quit
				    		println("goodbye")
				    		exit
				    	} 
				    	println("Please add time of event in integer form")
				    	var eventTime: String = readLine(); //get time of event
				    	var eventTimeInt: Int = eventTime.toInt;
				    	if(eventTime == "quit"){ //if user enters quit
				    		println("goodbye")
				    		exit
				    	}
				    	event_query ++= Seq( event_cc(eventname, eventPitch, eventDescription, eventStreet, eventCity, eventState, eventZipCode, eventDateInt, eventTimeInt,0,0)) // add values to table
				    	
				    	valid = true // exit loop 
				    	println("created new event.")
				    } while (!valid)	//while there is no valid answer, ask again
				    valid = false
		        } //end create event
				
			
				
				//Notifications
			    else if(mainOp == "5"){
			    	do{
			    		println("\n====You Have Mail From====")
			    		println("to exit program, type exit.")
			    		println("0- back")		    		
			    		println("1- Friend requests (" + friendinvite.length.run + ")") //print number of friend requests
			    		println("2- Group requests (" + groupinvite.length.run + ")") //print number of group requests
			    		
			    		var response = readLine().trim() //get user response
			    		if (response == "exit"){ //exit
			    		  println("Goodbye")
			    		  exit
			    		}
			    		else if (response == "0"){ //back
			    		  println("back")
			    		  valid = true
			    		}
			    		else if(response == "1") { //view friend requests
			    			valid = false
							do {
				    			println("\n=-=-=-= Friend Requests (" + friendinvite.length.run + ") =-=-=-=")
				    			println("Type exit to exit.")
				    			println("0- back")
				    		
								//Construct query finding events
								val inviteQuery = contactInv_query.filter(_.recip_id === currUserID) //friend invites sent to this user
								
								//print event list menu
								var idArray = new Array[Int](inviteQuery.length.run + 1) //create an array to store the request IDs
								var index = 1 				//index in the printout list and array
								for (request <- inviteQuery) { 	//for every request,
									val name = user_query.filter(_.user_id === request.sender_id).map(_.user_name).first()
									println(index + "- " + name) //print request info
									idArray(index) = request.invite_id.get //add this request ID to the array
									index += 1 //increment index
								} //end for each request
				    			
				    			response = readLine().trim() //get user response
				    			
				    			if(response == "exit") { //exit
				    			  println("goodbye!")
				    			  exit
				    			}
				    			else if (response == "0") { //back
				    			  println("back")
				    			  valid = true
				    			}
				    			//else if a numbered invite is selected
				    			else if (isAnInt(response)  &&  response.toInt > 0  &&  response.toInt <= inviteQuery.length.run) {
				    			  friendInvite(idArray(response.toInt)) //view the selected event
				    			}
				    			else { //else invalid response
				    			  println("Invalid response.")
				    			}
				    	
							} while (!valid); //repeat menu if not valid
							valid = false
			    		} //end friend requests
			    		
			    		else if(response == "2") { //group requests
			    			valid = false
							do {
				    			println("\n=-=-=-= Group Invites (" + groupinvite.length.run + ") =-=-=-=")
				    			println("Type exit to exit.")
				    			println("0- back")
				    		
								//Construct query finding events
								val inviteQuery = sgroupInv_query.filter(_.recip_id === currUserID) //friend invites sent to this user
								
								//print event list menu
								var idArray = new Array[Int](inviteQuery.length.run + 1) //create an array to store the request IDs
								var index = 1 				//index in the printout list and array
								for (request <- inviteQuery) { 	//for every request,
									val name = user_query.filter(_.user_id === request.sender_id).map(_.user_name).first()
									println(index + "- " + name) //print request info
									idArray(index) = request.invite_id.get //add this request ID to the array
									index += 1 //increment index
								} //end for each request
				    			
				    			response = readLine().trim() //get user response
				    			
				    			if(response == "exit") { //exit
				    			  println("goodbye!")
				    			  exit
				    			}
				    			else if (response == "0") { //back
				    			  println("back")
				    			  valid = true
				    			}
				    			//else if a numbered invite is selected,
				    			else if (isAnInt(response)  &&  response.toInt > 0  &&  response.toInt <= inviteQuery.length.run) {
				    			  groupInvite(idArray(response.toInt)) //view the selected group invite
				    			}
				    			else { //else invalid response
				    			  println("Invalid response.")
				    			}
							} while (!valid) //repeat menu if not valid
							valid = false
			    		} //end group invites
			    		
			    		else {
			    		  println("Invalid response.")
			    		}
			    	} while(!valid) //repeat if not valid
				      valid = false
			    } // end mainOp 5 (notifications)
				
				
			    //logout
			    else if (mainOp == "0"){
			      println(  "\nlogging out..." 
			    		  + "\nlogged out")
			      valid = true
			    }
			    
			    //invalid main menu option
			    else {
			      println("invalid option")
			    }
				
			  } while (!valid) //end main menu
			  valid = false
  
		  } while(!valid) //end logged-in session
				  
	  } while(!valid) //end UI
	  

	  println("DONE")
  }//Ends Session
}//Ends CassClassMapping app







///////////////////////////////// Case Classes ///////////////////////////////
//defines typed interface for each table row

case class user_cc(
    user_name: String, 
    user_password: String, 
    user_email: String, 
    user_id: Option[Int] = None
    )
case class event_cc(
    event_title: String, 
    event_pitch: String, 
    event_description: String, 
    event_street: String, 
    event_city: String, 
    event_state: String, 
    event_zip: String, 
    event_day: Int, 
    event_time: Int, 
    event_up: Int,  
    event_tieBreaker: Int, 
    event_id: Option[Int] = None
    )
case class sgroup_cc(
    sgroup_lead: Int, 
    sgroup_name: String, 
    sgroup_id: Option[Int] = None
    )
case class contact_cc(
    contact_owner_id: Int, 
    contactto_owner_id: Int, 
    contact_id: Option[Int] = None
    )
case class sgroupMem_cc(
    sgroup_id: Int, 
    member_id: Int, 
    sgroupMem_id: Option[Int] = None
    )
case class eventList_cc(
    sgroup_id: Int, 
    event_id: Int, 
    vote_up: Int,
    eventList_id: Option[Int] = None
    )
case class sgroupInv_cc(
    sgroup_id: Int,
    sender_id: Int,
    recip_id: Int,
    invite_id: Option[Int] = None
    )
case class contactInv_cc(
    sender_id: Int,
    reciep_id: Int,
    invite_id: Option[Int] = None
    )
case class vote_cc(
    voter_id: Int,
    vote_status: Boolean,
    vote_id: Option[Int] = None
    )

    
    
    
///////////////////////////////// Tables //////////////////////////////////////
//table definitions

//A User table with 4 columns: user_id, user_name, user_password, user_email
class user_table(tag: Tag) extends Table[user_cc](tag, "REG_USERS"){
    def user_id         = column[Int]("USER_ID", O.PrimaryKey, O.AutoInc) // This is the primary key column
    def user_name       = column[String]("USER_NAME")
    def user_password   = column[String]("USER_PASSWORD")
    def user_email      = column[String]("USER_EMAIL")
    
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (user_name, user_password, user_email, user_id.?) <> (user_cc.tupled, user_cc.unapply)
}

//An Events table with 14 columns: event_id, event_title, event_pitch, event_description, event_street, event_city, 
//event_state, event_zip, event_day, event_time, event_up, event_down
class event_table(tag: Tag) extends Table[event_cc](tag, "EVENTS"){
    def event_id        = column[Int]("EVENT_ID", O.PrimaryKey, O.AutoInc) //Primary Key
    def event_title     = column[String]("EVENT_TITLE")
    def event_pitch     = column[String]("EVENT_PITCH")
    def event_description = column[String]("EVENT_DESCRIPTION")
    def event_street    = column[String]("EVENT_STREET")
    def event_city      = column[String]("EVENT_CITY")
    def event_state     = column[String]("EVENT_STATE")
    def event_zip       = column[String]("EVENT_ZIP")
    def event_day       = column[Int]("EVENT_DAY")
    def event_time      = column[Int]("EVENT_TIME")
    def event_up        = column[Int]("EVENT_UP") //up-votes
    def event_tieBreaker = column[Int]("EVENT_TIEBREAKER")
    
    def *  = (event_title, event_pitch, event_description, event_street, event_city, event_state, event_zip, event_day, event_time, event_up, event_tieBreaker, event_id.?) <> (event_cc.tupled, event_cc.unapply)
}

//A Social Group table with 5 columns: sgroup_id, sgroup_lead, sgroup_name, sgroup_members, sgroup_events
class sgroup_table(tag : Tag) extends Table[sgroup_cc](tag, "SOCIALGROUP"){
    def sgroup_id: Column[Int]          = column[Int]("SGROUP_ID", O.PrimaryKey, O.AutoInc) //Primary Key
    def sgroup_lead: Column[Int]        = column[Int]("SGROUP_LEADER")
    def sgroup_name: Column[String]     = column[String]("SGROUP_NAME")
    
    def * = (sgroup_lead, sgroup_name, sgroup_id.?) <> (sgroup_cc.tupled, sgroup_cc.unapply)
    
    //Foreign Key(s)
    def leader: ForeignKeyQuery[user_table, user_cc] = foreignKey("SGROUP_LEADER", sgroup_lead, TableQuery[user_table])(_.user_id)
}

//A Contacts table with 2 columns:
class contact_table(tag : Tag) extends Table[contact_cc](tag, "CONTACTS"){
    def contact_id: Column[Int]         = column("CONTACT_ID", O.PrimaryKey, O.AutoInc)
    def contact_owner_id: Column[Int]   = column[Int]("CONTACTS_OWNER")
    def contactto_owner_id: Column[Int] = column[Int]("CONTACTTO_OWNER")
    
    def * = (contact_owner_id, contactto_owner_id, contact_id.?) <> (contact_cc.tupled, contact_cc.unapply)
    
    //Foreign Key(s)
    def owner: ForeignKeyQuery[user_table, user_cc] = foreignKey("CONTACTS_OWNER", contact_owner_id, TableQuery[user_table])(_.user_id)
    def contact: ForeignKeyQuery[user_table, user_cc] = foreignKey("CONTACTTO_OWNER", contactto_owner_id, TableQuery[user_table])(_.user_id)
}

//A Members table with 2 columns:
class sgroupMem_table(tag: Tag) extends Table[sgroupMem_cc](tag, "USER_TO_SGROUP"){
    def sgroupMem_id: Column[Int]   = column[Int]("ROW_ID", O.PrimaryKey, O.AutoInc)
    def sgroup_id: Column[Int]      = column[Int]("SGROUP_ID")
    def member_id: Column[Int]      = column[Int]("MEMBERS_ID")
    
    def * = (sgroup_id, member_id, sgroupMem_id.?) <> (sgroupMem_cc.tupled, sgroupMem_cc.unapply)
    
    //Foreign Key(s)
    def sgroup: ForeignKeyQuery[sgroup_table, sgroup_cc] = foreignKey("USER_TO_SGROUP", sgroup_id, TableQuery[sgroup_table])(_.sgroup_id)
    def member: ForeignKeyQuery[user_table, user_cc] = foreignKey("SGROUP_MEMBER", member_id, TableQuery[user_table])(_.user_id)
}

//A Events in group table with 2 columns:
class eventList_table(tag: Tag) extends Table[eventList_cc](tag, "LISTOF_EVENTS"){
    def eventList_id: Column[Int]   = column[Int]("LISTOF_EVENTS_ID", O.PrimaryKey, O.AutoInc)
    def sgroup_id: Column[Int]      = column[Int]("SGROUP_ID")
    def event_id: Column[Int]       = column[Int]("EVENTS_ID")
    def vote_up: Column[Int]        = column[Int]("VOTE_UP") //local up-votes
    
    def * = (sgroup_id, event_id, vote_up, eventList_id.?) <> (eventList_cc.tupled, eventList_cc.unapply)
    
    //Foreign Key(s)
    def sgroup: ForeignKeyQuery[sgroup_table, sgroup_cc] = foreignKey("EVENTS_TO_SGROUP", sgroup_id, TableQuery[sgroup_table])(_.sgroup_id)
    def event: ForeignKeyQuery[event_table, event_cc] = foreignKey("EVENTS_ID", event_id, TableQuery[event_table])(_.event_id)
}

//group invitations table
class sgroupInv_table(tag: Tag) extends Table[sgroupInv_cc](tag, "SOCIALGROUP_INVITE"){
    def invite_id: Column[Int]      = column[Int]("INVITE_ID", O.PrimaryKey, O.AutoInc)
    def sgroup_id: Column[Int]      = column[Int]("SGROUP_ID")
    def sender_id: Column[Int]      = column[Int]("SENDER_ID")
    def recip_id: Column[Int]       = column[Int]("RECIP_ID")
    
    def * = (sgroup_id, sender_id, recip_id, invite_id.?) <> (sgroupInv_cc.tupled, sgroupInv_cc.unapply)
    
    //Foreign Key(s)
    def sgroup: ForeignKeyQuery[sgroup_table, sgroup_cc] = foreignKey("INVITE_TO_SGROUP", sgroup_id, TableQuery[sgroup_table])(_.sgroup_id)
    def sgroupInv_sender: ForeignKeyQuery[user_table, user_cc] = foreignKey("SGROUP_INVITE_SENDER", sender_id, TableQuery[user_table])(_.user_id)
    def sgroup_recip: ForeignKeyQuery[user_table, user_cc] = foreignKey("SGROUP_INVITE_RECIP", recip_id, TableQuery[user_table])(_.user_id)
}

//contact invitations table
class contactInv_table(tag: Tag) extends Table[contactInv_cc](tag, "CONTACT_INVITE"){
    def invite_id: Column[Int]      = column[Int]("INVITE_ID", O.PrimaryKey, O.AutoInc)
    def sender_id: Column[Int]      = column[Int]("SENDER_ID")
    def recip_id: Column[Int]       = column[Int]("RECIP_ID")
    
    def * = (sender_id, recip_id, invite_id.?) <> (contactInv_cc.tupled, contactInv_cc.unapply)
    
    //Foreign Key(s)
    def contact_sender: ForeignKeyQuery[user_table, user_cc] = foreignKey("CONTACT_INVITE_SENDER", sender_id, TableQuery[user_table])(_.user_id)
    def contact_recip: ForeignKeyQuery[user_table, user_cc] = foreignKey("CONTACT_INVITE_RECIP", recip_id, TableQuery[user_table])(_.user_id)
}

//vote status table
class vote_table(tag: Tag) extends Table[vote_cc](tag, "VOTE"){
    def vote_id: Column[Int]            = column[Int]("VOTE_ID", O.PrimaryKey, O.AutoInc)
    def user_id: Column[Int]            = column[Int]("USER_ID")
    def vote_status: Column[Boolean]    = column[Boolean]("VOTE_UP_STATUS")
    
    def * = (user_id, vote_status, vote_id.?) <> (vote_cc.tupled, vote_cc.unapply)
    
    //Foreign Key(s)
    def voter: ForeignKeyQuery[user_table, user_cc] = foreignKey("VOTER", user_id, TableQuery[user_table])(_.user_id)
}


























