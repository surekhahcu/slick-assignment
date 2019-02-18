package edu.hcu.slick_assignment

import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

class SubjectRepository {


  val subjectTableQuery = TableQuery[Subjects]

  def create(subject: Subject): Future[Int] =
    DBConnection.db.run {
      (subjectTableAutoInc += subject)

    }

  def subjectTableAutoInc = subjectTableQuery returning subjectTableQuery.map(_.id)

  def delete(id: Int): Future[Int] = DBConnection.db.run {
    subjectTableQuery.filter(_.id === id).delete
  }

  def update(subject: Subject): Future[Int] = DBConnection.db.run {
    subjectTableQuery.filter(_.id === subject.id.get).update(subject)
  }

  def getById(id: Int): Future[Option[Subject]] = DBConnection.db.run {
    subjectTableQuery.filter(_.id === id).result.headOption
  }

  def getAll(): Future[List[Subject]] = DBConnection.db.run {
    subjectTableQuery.to[List].result
  }

  class Subjects(tag: Tag) extends Table[Subject](tag, "Subjects") {
    def * = (name, code, id.?) <> (Subject.tupled, Subject.unapply)

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def code = column[String]("code")
  }

  //  Await.result(DBConnection.db.run(subjectTableQuery.schema.create), 10 seconds)


}

case class Subject(name: String, code: String, id: Option[Int] = None)
