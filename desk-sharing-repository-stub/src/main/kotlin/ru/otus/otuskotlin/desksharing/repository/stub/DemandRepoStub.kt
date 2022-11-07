package ru.otus.otuskotlin.desksharing.repository.stub

import kotlinx.datetime.LocalDate
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.now
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandFilterRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandIdRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandResponse
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandsResponse
import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository
import ru.otus.otuskotlin.desksharing.stub.DeskSharingDemandStub

class DemandRepoStub() : IDemandRepository {
    override suspend fun createDemand(rq: DbDemandRequest): DbDemandResponse {
        return DbDemandResponse(
            data = DeskSharingDemandStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun readDemand(rq: DbDemandIdRequest): DbDemandResponse {
        return DbDemandResponse(
            data = DeskSharingDemandStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun updateDemand(rq: DbDemandRequest): DbDemandResponse {
        return DbDemandResponse(
            data = DeskSharingDemandStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun deleteDemand(rq: DbDemandIdRequest): DbDemandResponse {
        return DbDemandResponse(
            data = DeskSharingDemandStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun searchDemand(rq: DbDemandFilterRequest): DbDemandsResponse {
        return DbDemandsResponse(
            data = DeskSharingDemandStub.prepareSearchList(
                employeeId = DskShrngId("123"),
                dateFrom = LocalDate.now(),
                dateTo = LocalDate.now()
            ),
            isSuccess = true,
        )
    }
}
