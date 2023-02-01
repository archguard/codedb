package org.archguard.codedb.orchestration.schedule

class CronTime(
    val second: Int,
    val minute: Int,
    val hour: Int,
    val dayOfMonth: Int,
    val month: Int,
    val dayOfWeek: Int,
    val year: Int
) {
    fun from(str: String): CronTime {
        val tokens = str.split(" ")
        if (tokens.size != 6) {
            throw IllegalArgumentException("Invalid cron time format: $str")
        }
        return CronTime(
            second = tokens[0].toInt(),
            minute = tokens[1].toInt(),
            hour = tokens[2].toInt(),
            dayOfMonth = tokens[3].toInt(),
            month = tokens[4].toInt(),
            dayOfWeek = tokens[5].toInt(),
            year = 0
        )
    }

    companion object {
        fun from(expression: String): CronTime {
            if (expression.isEmpty()) {
                throw IllegalArgumentException("Invalid schedule expression: $expression")
            }

            // handle for like @daily
            if (expression.startsWith("@")) {
                val cronTime = CronTime(0, 0, 0, 0, 0, 0, 0)
                when (expression) {
                    "@daily" -> cronTime.from("0 0 0 * * ?")
                    "@weekly" -> cronTime.from("0 0 0 ? * MON")
                    "@monthly" -> cronTime.from("0 0 0 1 * ?")
                    "@yearly" -> cronTime.from("0 0 0 1 1 ?")
                    else -> throw IllegalArgumentException("Invalid schedule expression: $expression")
                }
            }

            if (expression.startsWith("cron:")) {
                val cronTime = CronTime(0, 0, 0, 0, 0, 0, 0)
                cronTime.from(expression.substring(5))

                return cronTime
            }

            throw IllegalArgumentException("Invalid schedule expression: $expression")
        }
    }
}