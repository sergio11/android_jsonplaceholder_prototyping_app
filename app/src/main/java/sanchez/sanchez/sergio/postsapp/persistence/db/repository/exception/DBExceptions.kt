package sanchez.sanchez.sergio.postsapp.persistence.db.repository.exception

import java.lang.Exception

/**
 * DB Exceptions
 */

abstract class DBException(message: String? = null, cause: Throwable? = null): Exception(message, cause)

/**
 * DB Error
 */
class DBErrorException(message: String? = null, cause: Throwable? = null): DBException(message, cause)

/**
 * DB No Result
 */
class DBNoResultException(message: String? = null, cause: Throwable? = null): DBException(message, cause)