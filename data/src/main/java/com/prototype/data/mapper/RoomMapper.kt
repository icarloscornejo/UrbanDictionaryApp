package com.prototype.data.mapper

// Used to map the ResponseModel to a EntityModel
// Clean Architecture

abstract class RoomMapper<out Entity : Any>: BaseMapper() {

    abstract fun toRoomEntity(): Entity
}