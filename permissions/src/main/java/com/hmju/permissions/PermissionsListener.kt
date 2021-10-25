package com.hmju.permissions

/**
 * Description :
 *
 * Created by hmju on 2021-10-25
 */
interface PermissionsListener {
    fun onResult(permissions: Map<String,Boolean>)
}