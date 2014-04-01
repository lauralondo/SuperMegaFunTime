import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ProvenShape, ForeignKeyQuery}

//A User table with 4 columns: user_id, user_name, user_password, user_email
class reg_users(tag: Tag) extends Table[(Int, String, String, String)](tag, "REG_USERS"){
    def user_id: Column[Int] = column[Int]("USER_ID", O.PrimaryKey) // This is the primary key column
    def user_name: Column[String] = column[String]("USER_NAME")
    def user_password: Column[String] = column[String]("USER_PASSWORD")
    def user_email: Column[String] = column[String]("USER_EMAIL")
    
    // Every table needs a * projection with the same type as the table's type parameter
    def * : ProvenShape[(Int, String, String, String)] = (user_id, user_name, user_password, user_email)
}

//An Events table with 14 columns: event_id, event_title, event_pitch, event_description, event_street, event_city, 
//event_state, event_zip, event_day, event_time, event_up, event_down
class reg_events(tag: Tag) extends Table[(Int, String, String, String, String, String, String, String, Int, Int, Int, Int, Int, Int)](tag, "EVENTS"){
    def reg_events_id: Column[Int] = column[Int]("EVENT_ID", O.PrimaryKey) //Primary Key
    def reg_events_title: Column[String] = column[String]("EVENT_TITLE")
    def reg_events_pitch: Column[String] = column[String]("EVENT_PITCH")
    def reg_events_description: Column[String] = column[String]("EVENT_DESCRIPTION")
    def reg_events_street: Column[String] = column[String]("EVENT_STREET")
    def reg_events_city: Column[String] = column[String]("EVENT_CITY")
    def reg_events_state: Column[String] = column[String]("EVENT_STATE")
    def reg_events_zip: Column[String] = column[String]("EVENT_ZIP")
    def reg_events_day: Column[Int] = column[Int]("EVENT_DAY")
    def reg_events_time: Column[Int] = column[Int]("EVENT_TIME")
    def reg_events_up: Column[Int] = column[Int]("EVENT_UP")
    def reg_events_down: Column[Int] = column[Int]("EVENT_DOWN")
    def reg_events_total: Column[Int] = column[Int]("EVENT_TOTAL")
    def reg_events_tieBreaker: Column[Int] = column[Int]("EVENT_TIEBREAKER")
    
    def * : ProvenShape[(Int, String, String, String, String, String, String, String, Int, Int, Int, Int, Int, Int)] = (reg_events_id, reg_events_title, reg_events_pitch, 
                reg_events_description, reg_events_street, reg_events_city, reg_events_state, reg_events_zip, reg_events_day, reg_events_time, reg_events_up, reg_events_down, reg_events_total, reg_events_tieBreaker)
}

//A Social Group table with 5 columns: sgroup_id, sgroup_lead, sgroup_name, sgroup_members, sgroup_events
class socialGroup(tag : Tag) extends Table[(Int, Int, String, Int, Int)](tag, "SOCIALGROUP"){
    def sgroup_id: Column[Int] = column[Int]("SGROUP_ID", O.PrimaryKey) //Primary Key
    def sgroup_lead: Column[Int] = column[Int]("USER_ID")
    def sgroup_name: Column[String] = column[String]("SGROUP_NAME")
    def sgroup_members: Column[Int] = column[Int]("LISTOF_MEMBERS")
    def sgroup_events: Column[Int] = column[Int]("LISTOF_EVENTS")
    
    def * : ProvenShape[(Int, Int, String, Int, Int)] = (sgroup_id, sgroup_lead, sgroup_name, sgroup_members, sgroup_events)
    
    //Foreign Key(s)
    def leader: ForeignKeyQuery[reg_users, (Int, String, String, String)] = foreignKey("SGROUP_LEADER", sgroup_lead, TableQuery[reg_users])(_.user_id)
    def listof_members: ForeignKeyQuery[listof_members, (Int, Int)] = foreignKey("LISTOF_MEMBERS", sgroup_members, TableQuery[listof_members])(_.listof_members_id)
    def listof_events: ForeignKeyQuery[listof_events, (Int, Int)] = foreignKey("LISTOF_EVENTS", sgroup_events, TableQuery[listof_events])(_.listof_events_id)
}

//A Members table with 2 columns:
class listof_members(tag: Tag) extends Table[(Int, Int)](tag, "LISTOF_MEMBERS"){
    def listof_members_id: Column[Int] = column[Int]("LISTOF_MEMBERS_ID", O.PrimaryKey)
    def members_id: Column[Int] = column[Int]("MEMBERS_ID")
    
    def * : ProvenShape[(Int, Int)] = (listof_members_id, members_id)
    
    //Foreign Key(s)
    def members: ForeignKeyQuery[reg_users, (Int, String, String, String)] = foreignKey("SGROUP_MEMBERS", members_id, TableQuery[reg_users])(_.user_id)
}

//A Events table with 2 columns:
class listof_events(tag: Tag) extends Table[(Int, Int)](tag, "LISTOF_EVENTS"){
    def listof_events_id: Column[Int] = column[Int]("LISTOF_EVENTS_ID", O.PrimaryKey)
    def reg_events_id: Column[Int] = column[Int]("EVENTS_ID")
    
    def * : ProvenShape[(Int, Int)] = (listof_events_id, reg_events_id)
    
    //Foreign Key(s)
    def reg_events: ForeignKeyQuery[reg_events, (Int, String, String, String, String, String, String, String, Int, Int, Int, Int, Int, Int)] = foreignKey("EVENTS_ID", reg_events_id, TableQuery[reg_events])(_.reg_events_id)
}









