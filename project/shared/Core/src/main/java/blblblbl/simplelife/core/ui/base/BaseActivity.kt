package blblblbl.simplelife.core.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity

/**
 * @author Kirill Tolmachev 04.08.2023
 */
private var isFirstInit = true
open class BaseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isFirstInit) {
            //предотвращаем возможное восстановление в onRestoreInstanceState
            savedInstanceState?.clear()
            /*Ситуация, когда приложения восстанавливается системой после убиства процесса,
            * ничего не востанавливаем,  потому что такое поведение, если специально о нем не
            * думать и специально его не поддерживать черевато ошибками и непонятными крешами при
            * разворачивании приложения. Поддерживать такое поведение довольно трудоемко, а тестировать
            * еще сложнее, а профит практически нулевой. */
            super.onCreate(null)
        } else {
            //Смена конфигурации
            super.onCreate(savedInstanceState)
        }
        isFirstInit = false
    }
}