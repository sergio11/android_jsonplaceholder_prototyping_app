package sanchez.sanchez.sergio.postsapp.persistence.api.exception

import java.lang.Exception


abstract class RepoException(cause: Throwable): Exception(cause)

/**
 * Repo Error Common Exception
 * @param cause
 */
class RepoErrorException(cause: Throwable): RepoException(cause)

/**
 * Repo No Result Exception
 * @param cause
 */
class RepoNoResultException(cause: Throwable): RepoException(cause)