package fr.backend.backend.exception

import java.util.*

class ResourceNotFoundException(
    message: String,
    val resourceId: UUID
) : RuntimeException(message)