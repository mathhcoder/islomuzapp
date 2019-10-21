package uz.islom.fiqh


fun grigorianToHijr(time: Long, adjustment: Int): String {

    val adjustedTime = time + adjustment * 86400

    val format = "dd MMM yyyy"
  //  val calendarSystem = IslamicChronology.getInstance()
   // val formatter = DateTimeFormat.forPattern(format).withChronology(calendarSystem)

   // return formatter.print(adjustedTime)

    return ""
}