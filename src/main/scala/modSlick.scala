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
  reg_users += (1, "Paul", "iamawesome", "123@123.com")
  reg_users += (2, "Laura", "123", "123@123.com")
  reg_users += (3, "Molly", "123", "123@123.com")
  reg_users += (4, "Jasper", "123", "123@123.com")
  reg_users += (5, "SAT", "123", "123@123.com")
  
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

  
  
  
  
  
  
  }
}