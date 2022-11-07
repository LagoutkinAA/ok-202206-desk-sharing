package ru.otus.otuskotlin.desksharing.common.repository

import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DemandError

data class DbDemandsResponse(
    override val data: List<DemandDto>?,
    override val isSuccess: Boolean,
    override val errors: List<DemandError> = emptyList(),
): IDbResponse<List<DemandDto>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbDemandsResponse(emptyList(), true)
        val MOCK_SUCCESS_NONE get() =  DbDemandsResponse(listOf(DemandDto.NONE), true)

        fun success(result: List<DemandDto>) = DbDemandsResponse(result, true)
        fun error(errors: List<DemandError>) = DbDemandsResponse(null, false, errors)
        fun error(error: DemandError) = DbDemandsResponse(null, false, listOf(error))
    }
}
