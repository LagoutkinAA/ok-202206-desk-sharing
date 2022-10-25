package ru.otus.otuskotlin.desksharing.common.repository

import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DemandError

data class DbDemandResponse(
    override val data: DemandDto?,
    override val isSuccess: Boolean,
    override val errors: List<DemandError> = emptyList()
): IDbResponse<DemandDto> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbDemandResponse(null, true)
        val MOCK_SUCCESS_NONE get() = DbDemandResponse(DemandDto.NONE, true)

        fun success(result: DemandDto) = DbDemandResponse(result, true)
        fun error(errors: List<DemandError>) = DbDemandResponse(null, false, errors)
        fun error(error: DemandError) = DbDemandResponse(null, false, listOf(error))
    }
}
