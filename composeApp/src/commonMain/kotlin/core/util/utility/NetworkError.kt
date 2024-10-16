package core.util.utility

import org.jetbrains.compose.resources.StringResource
import youtube.composeapp.generated.resources.Res
import youtube.composeapp.generated.resources.error_supabase_encoding
import youtube.composeapp.generated.resources.error_unknown

enum class NetworkError: Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN,
}

enum class SupabaseError: Error {
    ENCODING,
    UNKNOWN
}

internal fun SupabaseError.toStringResource(): StringResource {
    return when (this) {
        SupabaseError.ENCODING -> Res.string.error_supabase_encoding
        SupabaseError.UNKNOWN -> Res.string.error_unknown
    }
}