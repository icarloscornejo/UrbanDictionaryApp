package com.prototype.data.mapper

// Used to map the EntityModel to a DomainModel
// Clean Architecture

abstract class DomainMapper<Model : Any>: BaseMapper() {

    abstract fun toDomainModel(): Model
}