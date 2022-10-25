package ru.otus.otuskotlin.desksharing.repository.tests

import ru.otus.otuskotlin.desksharing.common.repository.DbDemandFilterRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandIdRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandResponse
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandsResponse
import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository

class DemandRepositoryMock(
    private val invokeCreateAd: (DbDemandRequest) -> DbDemandResponse = { DbDemandResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadAd: (DbDemandIdRequest) -> DbDemandResponse = { DbDemandResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateAd: (DbDemandRequest) -> DbDemandResponse = { DbDemandResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteAd: (DbDemandIdRequest) -> DbDemandResponse = { DbDemandResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchAd: (DbDemandFilterRequest) -> DbDemandsResponse = { DbDemandsResponse.MOCK_SUCCESS_EMPTY },
) : IDemandRepository {
    override suspend fun createDemand(rq: DbDemandRequest): DbDemandResponse {
        return invokeCreateAd(rq)
    }

    override suspend fun readDemand(rq: DbDemandIdRequest): DbDemandResponse {
        return invokeReadAd(rq)
    }

    override suspend fun updateDemand(rq: DbDemandRequest): DbDemandResponse {
        return invokeUpdateAd(rq)
    }

    override suspend fun deleteDemand(rq: DbDemandIdRequest): DbDemandResponse {
        return invokeDeleteAd(rq)
    }

    override suspend fun searchDemand(rq: DbDemandFilterRequest): DbDemandsResponse {
        return invokeSearchAd(rq)
    }
}
