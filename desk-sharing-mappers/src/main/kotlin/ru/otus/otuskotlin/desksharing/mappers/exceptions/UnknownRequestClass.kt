package ru.otus.otuskotlin.desksharing.mappers.exceptions

class UnknownRequestClass(clazz: Class<*>): RuntimeException("Class $clazz cannot be mapped to ru.otus.otuskotlin.desksharing.common.DemandContext")
