package ru.otus.otuskotlin.desksharing.biz

import ru.otus.otuskotlin.desksharing.biz.general.operation
import ru.otus.otuskotlin.desksharing.biz.stub.*
import ru.otus.otuskotlin.desksharing.biz.validation.*
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import ru.otus.otuskotlin.desksharing.cor.rootChain
import ru.otus.otuskotlin.desksharing.cor.worker

class DemandProcessor() {
    suspend fun exec(ctx: DemandContext) = BusinessChain.exec(ctx)

    companion object {
        @Suppress("DuplicatedCode")
        private val BusinessChain = rootChain<DemandContext> {
            initStatus("Инициализация статуса")

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
            }
            operation("Получить заявку", DemandCommand.READ) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
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
            }
            operation("Изменить заявку", DemandCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
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
            }
        }.build()
    }
}
