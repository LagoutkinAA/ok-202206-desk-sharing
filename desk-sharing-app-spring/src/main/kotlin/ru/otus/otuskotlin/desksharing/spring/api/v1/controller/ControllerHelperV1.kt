package ru.otus.otuskotlin.desksharing.spring.api.v1.controller

import fromTransport
import kotlinx.datetime.Clock
import org.springframework.security.oauth2.jwt.Jwt
import ru.otus.otuskotlin.deskSharing.api.v1.models.IRequest
import ru.otus.otuskotlin.deskSharing.api.v1.models.IResponse
import ru.otus.otuskotlin.desksharing.biz.DemandProcessor
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.helpers.asDemandError
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import toTransportDemand

suspend inline fun <reified Q : IRequest, reified R : IResponse> processV1(
    processor: DemandProcessor,
    command: DemandCommand? = null,
    request: Q,
    principal: Jwt
): R {
    val ctx = DemandContext(
        timeStart = Clock.System.now(),
    )
    return try {
        ctx.fromTransport(request)
        ctx.principal = principal.toModel()
        processor.exec(ctx)
        ctx.toTransportDemand() as R
    } catch (e: Throwable) {
        command?.also { ctx.command = it }
        ctx.state = DemandState.FAILING
        ctx.errors.add(e.asDemandError())
        processor.exec(ctx)
        ctx.toTransportDemand() as R
    }
}
