package ru.otus.otuskotlin.desksharing.repository.tests

import ru.otus.otuskotlin.desksharing.common.repository.DbDemandFilterRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandIdRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandResponse
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandsResponse
import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository

class DemandRepositoryMock(
    private val invokeCreateDemand: (DbDemandRequest) -> DbDemandResponse = { DbDemandResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadDemand: (DbDemandIdRequest) -> DbDemandResponse = { DbDemandResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateDemand: (DbDemandRequest) -> DbDemandResponse = { DbDemandResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteDemand: (DbDemandIdRequest) -> DbDemandResponse = { DbDemandResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchDemand: (DbDemandFilterRequest) -> DbDemandsResponse = { DbDemandsResponse.MOCK_SUCCESS_EMPTY },
) : IDemandRepository {
    override suspend fun createDemand(rq: DbDemandRequest): DbDemandResponse {
        return invokeCreateDemand(rq)
    }

    override suspend fun readDemand(rq: DbDemandIdRequest): DbDemandResponse {
        return invokeReadDemand(rq)
    }

    override suspend fun updateDemand(rq: DbDemandRequest): DbDemandResponse {
        return invokeUpdateDemand(rq)
    }

    override suspend fun deleteDemand(rq: DbDemandIdRequest): DbDemandResponse {
        return invokeDeleteDemand(rq)
    }

    override suspend fun searchDemand(rq: DbDemandFilterRequest): DbDemandsResponse {
        return invokeSearchDemand(rq)
    }
}
