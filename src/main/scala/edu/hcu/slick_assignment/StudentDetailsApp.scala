package edu.hcu.slick_assignment

import scala.concurrent.Await
import scala.concurrent.duration._

object StudentDetailsApp extends App {

  val students = new StudentRepository
  val subjects = new SubjectRepository
  val studentSubject = new StudentSubjectTableRepository


  /* Await.result(DBConnection.db.run(students.studentTableQuery.schema.create), 10 seconds)
   Await.result(DBConnection.db.run(subjects.subjectTableQuery.schema.create), 10 seconds)
   Await.result(DBConnection.db.run(studentSubject.studentSubjectTableQuery.schema.create), 10 seconds)



 val studentAndSubjectList: List[(Student, Subject)] =
   List(
       (Student("Radha", "radha@gmail.com", "Hyderabad"), Subject("Data Structures", "DS01")),
       (Student("Rakesh", "rakesh@gmail.com", "Hyderabad"), Subject("Algorithms", "Al01")),
       (Student("Rekha", "Rekha@gmail.com", "Hyderabad"), Subject("C language", "C01")),
       (Student("Rani", "Rani@gmail.com", "Hyderabad"), Subject("DBMS", "DB01")),
       (Student("Raman", "Raman@gmail.com", "Hyderabad"), Subject("Operating System", "OS01")),
       (Student("Sudha", "Sudha@gmail.com", "Hyderabad"), Subject("Computer Vision", "CV01")),
       (Student("Sneha", "Sneha@gmail.com", "Hyderabad"), Subject("Data Warehouse", "DW01")),
       (Student("Sonu", "Sonu@gmail.com", "Hyderabad"), Subject("Parallel Computing", "PC01")),
       (Student("Sanno", "Sanno@gmail.com", "Hyderabad"), Subject("Data Mining", "DM01")),
       (Student("Sam", "Sam@gmail.com", "Hyderabad"), Subject("Programming Methodology", "PM01"))

   )

 studentAndSubjectList.foreach { case (student, subject) =>
   val studentFuture: Future[Int] = students.create(student)

   val subjectFuture: Future[Int] = subjects.create(subject)

   val studentSubjectFuture: Future[(Int, Int)] = studentFuture.zip(subjectFuture)


   val finalResult: Future[Int] =
     studentSubjectFuture.flatMap { case (studentId, subjectId) =>
       studentSubject.save(StudentSubject(studentId, subjectId))
     }
   Await.result(finalResult, 10 seconds)
 }*/

  val studentWithMoreSubjects: List[StudentSubject] = List(StudentSubject(1, 3),
    StudentSubject(2, 3),
    StudentSubject(3, 3),
    StudentSubject(4, 3),
    StudentSubject(5, 6),
    StudentSubject(1, 4))
  studentWithMoreSubjects.foreach(studentSubjects => studentSubject.save(studentSubjects))


  val studentWithSubjects = Await.result(studentSubject.studentWithSubject(), 10 seconds)
  println(studentWithSubjects)

  val studentWithSubjectList = studentWithSubjects.groupBy { case (student, subject) => student }.map { case (student, subjectList) => (student, subjectList.map { case (student, subject) => subject }.toList) }
  println(studentWithSubjectList)

}
