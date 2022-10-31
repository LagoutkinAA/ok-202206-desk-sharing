package ru.otus.otuskotlin.desksharing.biz

import ru.otus.otuskotlin.desksharing.biz.general.initRepo
import ru.otus.otuskotlin.desksharing.biz.general.operation
import ru.otus.otuskotlin.desksharing.biz.general.prepareResult
import ru.otus.otuskotlin.desksharing.biz.repo.demandAssignNumber
import ru.otus.otuskotlin.desksharing.biz.repo.repoCreate
import ru.otus.otuskotlin.desksharing.biz.repo.repoDelete
import ru.otus.otuskotlin.desksharing.biz.repo.repoPrepareCreate
import ru.otus.otuskotlin.desksharing.biz.repo.repoPrepareDelete
import ru.otus.otuskotlin.desksharing.biz.repo.repoPrepareUpdate
import ru.otus.otuskotlin.desksharing.biz.repo.repoRead
import ru.otus.otuskotlin.desksharing.biz.repo.repoSearch
import ru.otus.otuskotlin.desksharing.biz.repo.repoUpdate
import ru.otus.otuskotlin.desksharing.biz.stub.initStatus
import ru.otus.otuskotlin.desksharing.biz.stub.stubCreateDeclined
import ru.otus.otuskotlin.desksharing.biz.stub.stubCreateSuccess
import ru.otus.otuskotlin.desksharing.biz.stub.stubDbError
import ru.otus.otuskotlin.desksharing.biz.stub.stubDeleteSuccess
import ru.otus.otuskotlin.desksharing.biz.stub.stubNoCase
import ru.otus.otuskotlin.desksharing.biz.stub.stubReadSuccess
import ru.otus.otuskotlin.desksharing.biz.stub.stubSearchSuccess
import ru.otus.otuskotlin.desksharing.biz.stub.stubUpdateSuccess
import ru.otus.otuskotlin.desksharing.biz.stub.stubValidationBadBookingDate
import ru.otus.otuskotlin.desksharing.biz.stub.stubValidationBadId
import ru.otus.otuskotlin.desksharing.biz.stub.stubs
import ru.otus.otuskotlin.desksharing.biz.validation.finishDemandValidation
import ru.otus.otuskotlin.desksharing.biz.validation.validateBookingDateInRange
import ru.otus.otuskotlin.desksharing.biz.validation.validateBookingDateNotEmpty
import ru.otus.otuskotlin.desksharing.biz.validation.validateDemandIdNotEmpty
import ru.otus.otuskotlin.desksharing.biz.validation.validateDemandIdProperFormat
import ru.otus.otuskotlin.desksharing.biz.validation.validateEmployeeIdNotEmpty
import ru.otus.otuskotlin.desksharing.biz.validation.validateEmployeeIdProperFormat
import ru.otus.otuskotlin.desksharing.biz.validation.validateUserIdNotEmpty
import ru.otus.otuskotlin.desksharing.biz.validation.validateUserIdProperFormat
import ru.otus.otuskotlin.desksharing.biz.validation.validation
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import ru.otus.otuskotlin.desksharing.common.model.DemandSettings
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.cor.chain
import ru.otus.otuskotlin.desksharing.cor.rootChain
import ru.otus.otuskotlin.desksharing.cor.worker

class DemandProcessor(private val settings: DemandSettings = DemandSettings()) {
    suspend fun exec(ctx: DemandContext) = BusinessChain.exec(ctx.apply { settings = this@DemandProcessor.settings })

    companion object {
        @Suppress("DuplicatedCode")
        private val BusinessChain = rootChain<DemandContext> {
            initStatus("Инициализация статуса")
            initRepo("Инициализация репозитория")

            operation("Создание заявки", DemandCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadBookingDate("Имитация ошибки валидации даты бронирования")
                    stubDbError("Имитация ошибки работы с БД")
                    stubCreateDeclined("Нет свободных рабочих мест")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в demandRequestValidating") {
                        demandRequestValidating = demandRequest.deepCopy()
                    }
                    validateBookingDateNotEmpty("Проверка что дата бронирования не пустая")
                    validateBookingDateInRange("Проверка что дата бронирования попадает в диапазон")
                    validateEmployeeIdNotEmpty("Проверка что id сотрудника не пустой")
                    validateEmployeeIdProperFormat("Проверка что id сотрудника соответствует формату")
                    validateUserIdNotEmpty("Проверка что id пользователя не пустой")
                    validateUserIdProperFormat("Проверка что id пользователя соответствует формату")

                    finishDemandValidation("Завершение проверок")
                }
                chain {
                    title = "Логика сохранения"
                    repoPrepareCreate("Подготовка объекта для сохранения")
                    repoCreate("Создание объявления в БД")
                    demandAssignNumber("Поиск свободного рабочего места")
                    repoUpdate("Обновление номера рабочего места")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Получить заявку", DemandCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в demandRequestValidating") {
                        demandRequestValidating = demandRequest.deepCopy()
                    }
                    validateDemandIdNotEmpty("Проверка что id заявки не пустой")
                    validateDemandIdProperFormat("Проверка что id заявки соответствует формату")

                    finishDemandValidation("Завершение проверок")
                }
                chain {
                    title = "Логика чтения"
                    repoRead("Чтение заявки из БД")
                    worker {
                        title = "Подготовка ответа для Read"
                        on { state == DemandState.RUNNING }
                        handle { demandRepoDone = demandRepoRead }
                    }
                }
                prepareResult("Подготовка ответа")
            }
            operation("Изменить заявку", DemandCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadBookingDate("Имитация ошибки валидации даты бронирования")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в demandRequestValidating") {
                        demandRequestValidating = demandRequest.deepCopy()
                    }
                    validateBookingDateNotEmpty("Проверка что дата бронирования не пустая")
                    validateBookingDateInRange("Проверка что дата бронирования попадает в диапазон")
                    validateEmployeeIdNotEmpty("Проверка что id сотрудника не пустой")
                    validateEmployeeIdProperFormat("Проверка что id сотрудника соответствует формату")
                    validateUserIdNotEmpty("Проверка что id пользователя не пустой")
                    validateUserIdProperFormat("Проверка что id пользователя соответствует формату")
                    validateDemandIdNotEmpty("Проверка что id заявки не пустой")
                    validateDemandIdProperFormat("Проверка что id заявки соответствует формату")

                    finishDemandValidation("Завершение проверок")
                }
                chain {
                    title = "Логика сохранения"
                    repoRead("Чтение заявки из БД")
                    repoPrepareUpdate("Подготовка объекта для обновления")
                    repoUpdate("Обновление заявки в БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Удалить заявку", DemandCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в demandRequestValidating") {
                        demandRequestValidating = demandRequest.deepCopy()
                    }
                    validateDemandIdNotEmpty("Проверка что id заявки не пустой")
                    validateDemandIdProperFormat("Проверка что id заявки соответствует формату")

                    finishDemandValidation("Завершение проверок")
                }
                chain {
                    title = "Логика удаления"
                    repoRead("Чтение заявки из БД")
                    repoPrepareDelete("Подготовка объекта для удаления")
                    repoDelete("Удаление заявки из БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Поиск заявок", DemandCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в demandRequestValidating") {
                        demandFilterRequestValidating = demandFilterRequest.copy()
                    }

                    finishDemandValidation("Завершение проверок")
                }
                chain {
                    title = "Логика поиска в БД"
                    repoSearch("Поиск заявок в БД")
                }
                prepareResult("Подготовка ответа")
            }
        }.build()
    }
}
