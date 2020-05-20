import java.math.BigInteger
import java.util.*
import kotlin.time.milliseconds

fun main (){
    val zaribDobandi = ZaribDobandi(50,5)

    //for check results
    println(zaribDobandi.methodA1().first)
    println(zaribDobandi.methodA2().first)
    println(zaribDobandi.methodA3().first)
    println(zaribDobandi.methodA4().first)
    println(zaribDobandi.methodB().first)
    println(zaribDobandi.methodC().first)
    println(zaribDobandi.methodD().first)

    //for check durations
    println("method A1 duration : ${getAverage(zaribDobandi,"A1")}")
    println("method A2 duration : ${getAverage(zaribDobandi,"A2")}")
    println("method A3 duration : ${getAverage(zaribDobandi,"A3")}")
    println("method A4 duration : ${getAverage(zaribDobandi,"A4")}")
    println("method B duration : ${getAverage(zaribDobandi,"B")}")
    println("method C duration : ${getAverage(zaribDobandi,"C")}")
    println("method D duration : ${getAverage(zaribDobandi,"D")}")
/*
    //for build csv (chart)
    println("A1,A2,A3,A4,B,C,D")
    var n  = 5
    while (n < 99999999999) {
        val zaribDobandi = ZaribDobandi(n, n/2)
        println("$n," +
                "${getAverage(zaribDobandi, "A1")}," +
                "${getAverage(zaribDobandi, "A2")}," +
                "${getAverage(zaribDobandi, "A3")}," +
                "${getAverage(zaribDobandi, "A4")}," +
                "${getAverage(zaribDobandi, "B")}," +
                "${getAverage(zaribDobandi, "C")}," +
                "${getAverage(zaribDobandi, "D")}")
        )
        n *= 2
    }
 */

}
fun getAverage(zaribDobandi: ZaribDobandi,type:String): Double {
    var methodAverage = 0.0
    for (i in (0..100)) {
        val method = when (type){
            "A1" -> zaribDobandi.methodA1()
            "A2" -> zaribDobandi.methodA2()
            "A3" -> zaribDobandi.methodA3()
            "A4" -> zaribDobandi.methodA4()
            "B" -> zaribDobandi.methodB()
            "C" -> zaribDobandi.methodC()
            "D" -> zaribDobandi.methodD()
            else -> zaribDobandi.methodA4()
        }
        methodAverage += method.second
    }
    methodAverage /= 100
    return methodAverage
}
class ZaribDobandi (n:Number,k:Number) { //BinomialCoefficient

    private val n = n.toInt()
    private val k = k.toInt()

    fun methodA1 () : Pair<BigInteger,Double> { // straight

        fun factorial (data : Int) : BigInteger {
            var result : BigInteger = 1.toBigInteger()
            (1..data).map {
                result *= it.toBigInteger();
            }
            return result;
        }

        if (n < k || n == k)
            return Pair(0.toBigInteger(),0.0)
        val time1 = Date()
        val result = factorial(n)/(factorial(k)*factorial(n-k))
        val time2 = Date()
        return Pair(result,(time2.time.milliseconds - time1.time.milliseconds).inMilliseconds)
    }
    fun methodA2 () : Pair<BigInteger,Double> { // recursion

        fun factorial (data : Int) : BigInteger {
            return when(data){
                0 -> 1.toBigInteger()
                else -> data.toBigInteger() * factorial(data - 1)
            }
        }

        if (n < k || n == k)
            return Pair(0.toBigInteger(),0.0)
        val time1 = Date()
        val result = factorial(n)/(factorial(k)*factorial(n-k))
        val time2 = Date()
        return Pair(result,(time2.time.milliseconds - time1.time.milliseconds).inMilliseconds)
    }
    fun methodA3 () : Pair<BigInteger,Double> { // recursion with reminder function

        val factorial = mutableListOf<BigInteger>()
        factorial.add(1.toBigInteger())

        fun factorial (data : Int) : BigInteger {
            factorial.getOrNull(data)?.let {
                return it
            }
            factorial.add(data, data.toBigInteger() * factorial(data - 1))
            return factorial[data]
        }

        if (n < k || n == k)
            return Pair(0.toBigInteger(),0.0)
        val time1 = Date()
        val result = factorial(n)/(factorial(k)*factorial(n-k))
        val time2 = Date()
        return Pair(result,(time2.time.milliseconds - time1.time.milliseconds).inMilliseconds)
    }
    fun methodA4 () : Pair<BigInteger,Double> { // dynamic

        val factorial = mutableListOf<BigInteger>()
        factorial.add(1.toBigInteger())

        fun factorial (data : Int) : BigInteger {
            factorial.getOrNull(data)?.let {
                return it
            }
            for (i in (1..data)) {
                if (factorial.getOrNull(i) == null){
                    factorial.add(i, i.toBigInteger() * factorial[i-1])
                }
            }
            return factorial[data]
        }

        if (n < k || n == k)
            return Pair(0.toBigInteger(),0.0)
        val time1 = Date()
        val result = factorial(n)/(factorial(k)*factorial(n-k))
        val time2 = Date()
        return Pair(result,(time2.time.milliseconds - time1.time.milliseconds).inMilliseconds)
    }
    fun methodB () : Pair<BigInteger,Double> { // recursion

        fun result (n : Int,k : Int) : BigInteger {
            return when {
                k == 0 || n == k -> 1.toBigInteger()
                else -> result(n-1,k-1) + result(n-1 , k)
            }
        }

        if (n < k || n == k)
            return Pair(0.toBigInteger(),0.0)
        val time1 = Date()
        val result = result(n,k)
        val time2 = Date()
        return Pair(result,(time2.time.milliseconds - time1.time.milliseconds).inMilliseconds)
    }
    fun methodC () : Pair<BigInteger,Double> { // recursion with reminder function
        val results = MutableList(n+1){MutableList<BigInteger?>(n+1){null} }

        fun result (n : Int,k : Int) : BigInteger {
            results.getOrNull(n)?.getOrNull(k)?.let {
                return it
            }

            when {
                k == 0 || n == k -> return 1.toBigInteger()
                else -> results[n][k] = result(n-1,k-1) + result(n-1 , k)
            }
            return results[n][k]!!
        }

        if (n < k || n == k)
            return Pair(0.toBigInteger(),0.0)
        val time1 = Date()
        val result = result(n,k)
        val time2 = Date()
        return Pair(result,(time2.time.milliseconds - time1.time.milliseconds).inMilliseconds)
    }
    fun methodD () : Pair<BigInteger,Double> { // dynamic

        val results = MutableList(n+1){MutableList<BigInteger?>(n+1){null} }

        fun result (n : Int,k : Int) : BigInteger {
            results[n][k]?.let {
                return it
            }
            for (i in (0..n)){
                for (j in (0..k)){
                    if (results[i][j] == null){
                        if (i == 0 || i == j || j == 0){
                            results[i][j] = 1.toBigInteger()
                        }else {
                            results[i][j] = results[i-1][j-1]!! + results[i-1][j]!!
                        }
                    }
                }
            }
            return results[n][k]!!
        }

        if (n < k || n == k)
            return Pair(0.toBigInteger(),0.0)
        val time1 = Date()
        val result = result(n,k)
        val time2 = Date()
        return Pair(result,(time2.time.milliseconds - time1.time.milliseconds).inMilliseconds)
    }
}
