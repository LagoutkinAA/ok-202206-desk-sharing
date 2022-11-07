package ru.otus.otuskotlin.desksharing.common.repository

interface IDemandRepository {
    suspend fun createDemand(rq: DbDemandRequest): DbDemandResponse
    suspend fun readDemand(rq: DbDemandIdRequest): DbDemandResponse
    suspend fun updateDemand(rq: DbDemandRequest): DbDemandResponse
    suspend fun deleteDemand(rq: DbDemandIdRequest): DbDemandResponse
    suspend fun searchDemand(rq: DbDemandFilterRequest): DbDemandsResponse
    companion object {
        val NONE = object : IDemandRepository {
            override suspend fun createDemand(rq: DbDemandRequest): DbDemandResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readDemand(rq: DbDemandIdRequest): DbDemandResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateDemand(rq: DbDemandRequest): DbDemandResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteDemand(rq: DbDemandIdRequest): DbDemandResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchDemand(rq: DbDemandFilterRequest): DbDemandsResponse {
                TODO("Not yet implemented")
            }
        }
    }
}
