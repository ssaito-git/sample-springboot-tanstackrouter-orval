package samplespringboottanstackrouterorval.util

import com.fasterxml.uuid.impl.UUIDUtil
import java.util.UUID

fun String.toUuidOrNull(): UUID? {
    return runCatching { UUIDUtil.uuid(this) }.getOrNull()
}
