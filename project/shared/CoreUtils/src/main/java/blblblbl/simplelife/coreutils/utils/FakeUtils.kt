package blblblbl.simplelife.coreutils.utils

import kotlin.reflect.KClass

/**
 * @author Kirill Tolmachev 04.08.2023
 */

/**
 * Создает фейковый объект для json подобного класса, полезен для preview
 * Специальные типы можно замокать с помощью [specialFactories]
 * пример:  fake(Long::class to { 123123 }, String::class to { "Some text" }))
 *
 * источник: https://stackoverflow.com/a/70892397/12672397
 *
 */
inline fun <reified T : Any> fake(vararg specialFactories: Pair<KClass<*>, () -> Any?>) =
    Fakes.fakeImpl(T::class, specialFactories.toList()) as T


object Fakes {
    fun <T : Any> fakeImpl(
        kClass: KClass<T>,
        specialFactories: List<Pair<KClass<*>, () -> Any?>>
    ) = createFakeObject(kClass.java, specialFactories)

    private val DEFAULT_FACTORIES = listOf(
        Info(listOf(Int::class.java, java.lang.Integer::class.java)) { 0 },
        Info(listOf(Long::class.java, java.lang.Long::class.java)) { 0L },
        Info(listOf(Double::class.java, java.lang.Double::class.java)) { 0.0 },
        Info(listOf(Float::class.java, java.lang.Float::class.java)) { 0.0f },
        Info(listOf(Boolean::class.java, java.lang.Boolean::class.java)) { true },
        Info(listOf(String::class.java, java.lang.String::class.java)) { "" },
        Info(
            listOf(List::class.java, MutableList::class.java, java.util.List::class.java)
        ) { ArrayList<Any?>() },
        Info(
            listOf(Map::class.java, MutableMap::class.java, java.util.Map::class.java)
        ) { HashMap<Any?, Any?>() },
    )

    private fun createFakeObject(
        clazz: Class<*>,
        specialFactories: List<Pair<KClass<*>, () -> Any?>> = emptyList()
    ): Any {
        // special check, notice it is "==" not "isAssignableFrom"
        if (clazz == Any::class.java || clazz == Object::class.java) {
            return Object()
        }


        val specialFactoryResult =
            specialFactories.firstOrNull { it.first.java == clazz }?.second?.invoke()
        if (specialFactoryResult != null) {
            return specialFactoryResult
        }


        return DEFAULT_FACTORIES
            .firstOrNull { info -> info.classes.any { infoClazz -> infoClazz.isAssignableFrom(clazz) } }
            ?.defaultValue?.invoke()
            ?: createFakeObjectByConstructor(clazz, specialFactories)
    }

    private fun createFakeObjectByConstructor(
        clazz: Class<*>,
        specialFactories: List<Pair<KClass<*>, () -> Any?>>
    ): Any {
        if (clazz.isEnum) {
            return getEnumValues(clazz).first()!!
        }

        val ctor = clazz.constructors.first()
        val args = ctor.parameterTypes.map { createFakeObject(it, specialFactories) }.toTypedArray()
        ctor.isAccessible = true
        return ctor.newInstance(*args)

    }

    private fun getEnumValues(enumClass: Class<*>): Array<*> {
        val f = enumClass.getDeclaredField("\$VALUES")
        f.isAccessible = true
        return f.get(null) as Array<*>
    }

    private data class Info(
        val classes: List<Class<*>>,
        val defaultValue: () -> Any,
    )
}