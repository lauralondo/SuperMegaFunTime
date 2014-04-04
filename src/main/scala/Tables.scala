import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ProvenShape, ForeignKeyQuery}

//You have 6 tables

//Foreign Key template
//def owner: ForeignKeyQuery[, ()] = foreignKey("", , TableQuery[])(_.)
//The name of each relationship needs to be unique

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
class sgroup(tag : Tag) extends Table[(Int, Int, String)](tag, "SOCIALGROUP"){
    def sgroup_id: Column[Int] = column[Int]("SGROUP_ID", O.PrimaryKey) //Primary Key
    def sgroup_lead: Column[Int] = column[Int]("SGROUP_LEADER")
    def sgroup_name: Column[String] = column[String]("SGROUP_NAME")
    
    def * : ProvenShape[(Int, Int, String)] = (sgroup_id, sgroup_lead, sgroup_name)
    
    //Foreign Key(s)
    def leader: ForeignKeyQuery[reg_users, (Int, String, String, String)] = foreignKey("SGROUP_LEADER", sgroup_lead, TableQuery[reg_users])(_.user_id)
}

//A Contacts table with 2 columns:
class contacts(tag : Tag) extends Table[(Int, Int, Int)](tag, "CONTACTS"){
    def contact_id: Column[Int] = column("CONTACT_ID", O.PrimaryKey)
    def contacts_owner_id: Column[Int] = column[Int]("CONTACTS_OWNER")
    def contactto_owner_id: Column[Int] = column[Int]("CONTACTTO_OWNER")
    
    def * : ProvenShape[(Int, Int, Int)] = (contact_id, contacts_owner_id, contactto_owner_id)
    
    //Foreign Key(s)
    def owner: ForeignKeyQuery[reg_users, (Int, String, String, String)] = foreignKey("CONTACTS_OWNER", contacts_owner_id, TableQuery[reg_users])(_.user_id)
    def contact: ForeignKeyQuery[reg_users, (Int, String, String, String)] = foreignKey("CONTACTTO_OWNER", contactto_owner_id, TableQuery[reg_users])(_.user_id)
}

//A Members table with 2 columns:
class user_to_sgroup(tag: Tag) extends Table[(Int, Int, Int)](tag, "USER_TO_SGROUP"){
    def user_to_sgroup_id: Column[Int] = column[Int]("ROW_ID", O.PrimaryKey)
    def sgroup_id: Column[Int] = column[Int]("SGROUP_ID")
    def members_id: Column[Int] = column[Int]("MEMBERS_ID")
    
    def * : ProvenShape[(Int, Int, Int)] = (user_to_sgroup_id, sgroup_id, members_id)
    
    //Foreign Key(s)
    def user_to_sgroup: ForeignKeyQuery[sgroup, (Int, Int, String)] = foreignKey("USER_TO_SGROUP", sgroup_id, TableQuery[sgroup])(_.sgroup_id)
    def member: ForeignKeyQuery[reg_users, (Int, String, String, String)] = foreignKey("SGROUP_MEMBER", members_id, TableQuery[reg_users])(_.user_id)
}

//A Events table with 2 columns:
class listof_events(tag: Tag) extends Table[(Int, Int, Int)](tag, "LISTOF_EVENTS"){
    def listof_events_id: Column[Int] = column[Int]("LISTOF_EVENTS_ID", O.PrimaryKey)
    def sgroup_id: Column[Int] = column[Int]("SGROUP_ID")
    def events_id: Column[Int] = column[Int]("EVENTS_ID")
    
    def * : ProvenShape[(Int, Int, Int)] = (listof_events_id, sgroup_id, events_id)
    
    //Foreign Key(s)
    def events_to_sgroup_id: ForeignKeyQuery[sgroup, (Int, Int, String)] = foreignKey("EVENTS_TO_SGROUP", sgroup_id, TableQuery[sgroup])(_.sgroup_id)
    def events: ForeignKeyQuery[reg_events, (Int, String, String, String, String, String, String, String, Int, Int, Int, Int, Int, Int)] = foreignKey("EVENTS_ID", events_id, TableQuery[reg_events])(_.reg_events_id)
}













