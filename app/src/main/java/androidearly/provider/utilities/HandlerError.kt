package androidearly.provider.utilities

import retrofit2.HttpException
import java.io.IOException

object HandlerError {
    fun getErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is HttpException -> {
                "Error en la petición HTTP: ${throwable.code()}"
            }

            is IOException -> {
                "Error de red: ${throwable.message}"
            }

            else -> {
                "Error desconocido: ${throwable.message}"
            }
        }
    }
}