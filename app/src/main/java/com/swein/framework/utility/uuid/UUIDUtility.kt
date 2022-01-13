package com.swein.framework.utility.uuid

import java.util.*

class UUIDUtility {
    companion object {
        fun getUUIDString(): String {
            return UUID.randomUUID().toString().replace("-", "")
        }
    }
}