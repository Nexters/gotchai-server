package com.gotchai.storage.rdb.global.util

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDsl
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryWhereStep
import kotlin.reflect.KClass

class JdslUtil : Jpql() {
    companion object Constructor : JpqlDsl.Constructor<JdslUtil> {
        override fun newInstance(): JdslUtil = JdslUtil()
    }

    inline fun <reified T : Any> selectFrom(type: KClass<T>): SelectQueryWhereStep<T> {
        val entity = entity(type)
        return select(entity).from(entity)
    }
}
