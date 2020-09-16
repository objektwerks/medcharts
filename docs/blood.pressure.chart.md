Chart
-----
>Composed of 2 Line charts, with:
1. X-Axis : Day,Hour ( d,H )
2. Y-Axis : Blood Pressure ( systolic )
3. Tooltip ( systolic / diastolic, deltaSystolic / deltaDiastolic )
4. Legend : Blood Pressure (Red)
5. Title : Blood Pressure : (y.m.d - y.m.d)

Model
-----
1. Blood Pressure: datetime, systolic (1), diastolic (2)

Constraints
-----------
1. >= 120 <= 200
2. >= 80 <= 120

Blood Pressure
--------------
```scala
final case class BloodPressure(datetime: Minute, systolic: Int, diastolic: Int)
```

Blood Pressure CSV
------------------
>See data/bloodpressure/blood-pressure.txt
1. datetime - yyyy-MM-ddThh:mm:ss ( 2020-07-04T10:04:00 )
2. systolic - nnn
3. diastolic - nnn