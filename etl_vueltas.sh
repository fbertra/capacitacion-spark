# 

export SPARK_HOME=$HOME/swf/spark/spark-2.3.4-bin-hadoop2.6

MAIN_CLASS=cl.fbd.capacitacion_spark.Csv2ParquetMain
JAR=target/scala-2.11/capacitacion-spark_2.11-0.1.0-SNAPSHOT.jar

CSV=data/csv/vueltas-2015-2017.csv
PARQUET=data/parquet/vueltas.parquet

$SPARK_HOME/bin/spark-submit --class $MAIN_CLASS $JAR $CSV $PARQUET 2>stderr | tee -a stdout