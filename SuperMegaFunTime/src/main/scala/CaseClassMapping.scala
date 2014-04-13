import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ProvenShape, ForeignKeyQuery}

object CaseClassMapping extends App {

  //val reg_users: TableQuery[reg_users]
  //ERROR:only classes can have declared but undefined members
  
  //The query interface for the * table
  val user_query = TableQuery[user_table]
  val event_query = TableQuery[event_table]
  val sgroup_query = TableQuery[sgroup_table]
  val contact_query = TableQuery[contact_table]
  val sgroupMem_query = TableQuery[sgroupMem_table]
  val eventList_query= TableQuery[eventList_table]
  
  //Create a connection (called a "session") to an in-memory H2 database
  Database.forURL("jdbc:h2:mem:hello", driver = "org.h2.Driver").withSession { implicit session =>
  
  //Create the schema by combining the DDLs for the tables using the query interfaces
  (user_query.ddl ++ event_query.ddl ++ sgroup_query.ddl ++ contact_query.ddl ++ 
  sgroupMem_query.ddl ++ eventList_query.ddl).create


  //Insert here
  val user_insert: Option[Int] = user_query ++= Seq(
      user_cc("paul", "Forte_1234", "paulnguyen@sandiego.edu"),
      user_cc("laura", "123", "123@123.com"),
      user_cc("molly", "123", "123@123.com"),
      user_cc("jasper", "123", "123@123.com"),
      user_cc("sat", "123", "123@123.com")
      )
  
  val event_insert: Option[Int] = event_query ++= Seq(
      event_cc("TacoBell","PartyHard","Party with TacoBell","Balboa","San Diego","CA","92101", 0,21,0,0,0,0),
      event_cc("Board Games","PartyHard","Party with TacoBell","Balboa","San Diego","CA","92101", 0,21,0,0,0,0),
      event_cc("Star Trek","PartyHard","Party with TacoBell","Balboa","San Diego","CA","92101", 0,21,0,0,0,0),
      event_cc("Comic Con","PartyHard","Party with TacoBell","Balboa","San Diego","CA","92101", 0,21,0,0,0,0),
      event_cc("Chargers","PartyHard","Party with TacoBell","Balboa","San Diego","CA","92101", 0,21,0,0,0,0)
      )
      
  val sgroup_insert: Option[Int] = sgroup_query ++= Seq(
      sgroup_cc(1, "Paul's Group"),
      sgroup_cc(3, "Molly's Group")
      )
      
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
  val sgroupMem_insert: Option[Int] = sgroupMem_query ++= Seq(
      sgroupMem_cc(1,2),
      sgroupMem_cc(1,4),
      sgroupMem_cc(1,5),
      sgroupMem_cc(2,2),
      sgroupMem_cc(2,4)
      )
      
  val eventList_insert: Option[Int]= eventList_query ++= Seq(
      eventList_cc(1,1),
      eventList_cc(1,4),
      eventList_cc(2,3),
      eventList_cc(2,4),
      eventList_cc(2,5)
      )
  
  //print here
  println(user_query.list)
  println(event_query.list)
  println(sgroup_query.list)
  println(contact_query.list)
  println(sgroupMem_query.list)
  println(eventList_query.list)
  
  
  /////////////////////////////// Queries ////////////////////////////////////////



  //val composedQuery
  //val namesQuery: Query[Column [String], String] = user_cc.sortBy(_.user_name).map(_.user_name)
  val namesQuery = user_query.sortBy(_.user_name).map(_.user_name)
  println(namesQuery.list)
  
  //Construct query finding events
  //val eventQuery: Query[Column[String], String] = reg_events.sortBy(_.reg_events_id).map(_.reg_events_title)
  val eventQuery = event_query.sortBy(_.event_id).map(_.event_title)
  println(eventQuery.list)
  
  //Constrct query finding sgroup
  //val sgroupQuery: Query[Column[Int], Int] = sgroup.sortBy(_.sgroup_id).map(_.sgroup_lead)
  val sgroupQuery = sgroup_query.sortBy(_.sgroup_id).map(_.sgroup_lead)
  println(sgroupQuery.list)
  
  //Construct query finding contacts of user
  //val contactsQuery: Query[Column[Int], Int] = contacts.filter(_.contacts_owner_id === 2).map(_.contactto_owner_id)
  val contactsQuery = contact_query.filter(_.contact_owner_id === 2).map(_.contactto_owner_id)
  println(contactsQuery.list)
  
  //Construct query finding contacts of user
  //val userToSgroupQuery: Query[Column[Int], Int] = user_to_sgroup.sortBy(_.user_to_sgroup_id).map(_.user_to_sgroup_id)
  val userToSgroupQuery = sgroupMem_query.sortBy(_.sgroup_id).map(_.sgroup_id)
  println(userToSgroupQuery.list)
  
  //val joinQuery: Query[(Column[String]), (String)] = for {
  //   g <- listof_events if g.sgroup_id === 1 //get the sgroup
  //   e <- g.events
  //} yield(e.reg_events_title)
  
  val joinQuery = for{
      g <- eventList_query if g.sgroup_id === 1
      e <- g.event
  } yield(e.event_title)
  
  println(joinQuery.list)








  
  
  
  }//Ends Session
}//Ends CassClassMapping

/////////////////////////////////Class Casses///////////////////////////////
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
    event_down: Int, 
    event_total: Int, 
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
    eventList_id: Option[Int] = None
    )

///////////////////////////////// Tables //////////////////////////////////////

//A User table with 4 columns: user_id, user_name, user_password, user_email
class user_table(tag: Tag) extends Table[user_cc](tag, "REG_USERS"){
    def user_id = column[Int]("USER_ID", O.PrimaryKey, O.AutoInc) // This is the primary key column
    def user_name = column[String]("USER_NAME")
    def user_password = column[String]("USER_PASSWORD")
    def user_email = column[String]("USER_EMAIL")
    
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (user_name, user_password, user_email, user_id.?) <> (user_cc.tupled, user_cc.unapply)
}

//An Events table with 14 columns: event_id, event_title, event_pitch, event_description, event_street, event_city, 
//event_state, event_zip, event_day, event_time, event_up, event_down
class event_table(tag: Tag) extends Table[event_cc](tag, "EVENTS"){
    def event_id = column[Int]("EVENT_ID", O.PrimaryKey, O.AutoInc) //Primary Key
    def event_title = column[String]("EVENT_TITLE")
    def event_pitch = column[String]("EVENT_PITCH")
    def event_description = column[String]("EVENT_DESCRIPTION")
    def event_street = column[String]("EVENT_STREET")
    def event_city = column[String]("EVENT_CITY")
    def event_state = column[String]("EVENT_STATE")
    def event_zip = column[String]("EVENT_ZIP")
    def event_day = column[Int]("EVENT_DAY")
    def event_time = column[Int]("EVENT_TIME")
    def event_up = column[Int]("EVENT_UP")
    def event_down = column[Int]("EVENT_DOWN")
    def event_total = column[Int]("EVENT_TOTAL")
    def event_tieBreaker = column[Int]("EVENT_TIEBREAKER")
    
    def *  = (event_title, event_pitch, event_description, event_street, event_city, event_state, event_zip, event_day, event_time, event_up, event_down, event_total, event_tieBreaker, event_id.?) <> (event_cc.tupled, event_cc.unapply)
}

//A Social Group table with 5 columns: sgroup_id, sgroup_lead, sgroup_name, sgroup_members, sgroup_events
class sgroup_table(tag : Tag) extends Table[sgroup_cc](tag, "SOCIALGROUP"){
    def sgroup_id: Column[Int] = column[Int]("SGROUP_ID", O.PrimaryKey, O.AutoInc) //Primary Key
    def sgroup_lead: Column[Int] = column[Int]("SGROUP_LEADER")
    def sgroup_name: Column[String] = column[String]("SGROUP_NAME")
    
    def * = (sgroup_lead, sgroup_name, sgroup_id.?) <> (sgroup_cc.tupled, sgroup_cc.unapply)
    
    //Foreign Key(s)
    def leader: ForeignKeyQuery[user_table, user_cc] = foreignKey("SGROUP_LEADER", sgroup_lead, TableQuery[user_table])(_.user_id)
}

//A Contacts table with 2 columns:
class contact_table(tag : Tag) extends Table[contact_cc](tag, "CONTACTS"){
    def contact_id: Column[Int] = column("CONTACT_ID", O.PrimaryKey, O.AutoInc)
    def contact_owner_id: Column[Int] = column[Int]("CONTACTS_OWNER")
    def contactto_owner_id: Column[Int] = column[Int]("CONTACTTO_OWNER")
    
    def * = (contact_owner_id, contactto_owner_id, contact_id.?) <> (contact_cc.tupled, contact_cc.unapply)
    
    //Foreign Key(s)
    def owner: ForeignKeyQuery[user_table, user_cc] = foreignKey("CONTACTS_OWNER", contact_owner_id, TableQuery[user_table])(_.user_id)
    def contact: ForeignKeyQuery[user_table, user_cc] = foreignKey("CONTACTTO_OWNER", contactto_owner_id, TableQuery[user_table])(_.user_id)
}

//A Members table with 2 columns:
class sgroupMem_table(tag: Tag) extends Table[sgroupMem_cc](tag, "USER_TO_SGROUP"){
    def sgroupMem_id: Column[Int] = column[Int]("ROW_ID", O.PrimaryKey, O.AutoInc)
    def sgroup_id: Column[Int] = column[Int]("SGROUP_ID")
    def member_id: Column[Int] = column[Int]("MEMBERS_ID")
    
    def * = (sgroup_id, member_id, sgroupMem_id.?) <> (sgroupMem_cc.tupled, sgroupMem_cc.unapply)
    
    //Foreign Key(s)
    def sgroup: ForeignKeyQuery[sgroup_table, sgroup_cc] = foreignKey("USER_TO_SGROUP", sgroup_id, TableQuery[sgroup_table])(_.sgroup_id)
    def member: ForeignKeyQuery[user_table, user_cc] = foreignKey("SGROUP_MEMBER", member_id, TableQuery[user_table])(_.user_id)
}

//A Events table with 2 columns:
class eventList_table(tag: Tag) extends Table[eventList_cc](tag, "LISTOF_EVENTS"){
    def eventList_id: Column[Int] = column[Int]("LISTOF_EVENTS_ID", O.PrimaryKey, O.AutoInc)
    def sgroup_id: Column[Int] = column[Int]("SGROUP_ID")
    def event_id: Column[Int] = column[Int]("EVENTS_ID")
    
    def * = (sgroup_id, event_id, eventList_id.?) <> (eventList_cc.tupled, eventList_cc.unapply)
    
    //Foreign Key(s)
    def sgroup: ForeignKeyQuery[sgroup_table, sgroup_cc] = foreignKey("EVENTS_TO_SGROUP", sgroup_id, TableQuery[sgroup_table])(_.sgroup_id)
    def event: ForeignKeyQuery[event_table, event_cc] = foreignKey("EVENTS_ID", event_id, TableQuery[event_table])(_.event_id)
}




























