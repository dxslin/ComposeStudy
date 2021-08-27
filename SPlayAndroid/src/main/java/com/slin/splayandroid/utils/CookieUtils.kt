package com.slin.splayandroid.utils

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.preferencesKey
import com.slin.splayandroid.ext.getValue
import com.slin.splayandroid.ext.setValue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

/**
 * author: slin
 * date: 2021/8/26
 * description:
 *
 */

object CookieUtils {

    /**
     * save cookie string
     */
    internal fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
            .map { cookie ->
                cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            }
            .forEach { it ->
                it.filterNot { set.contains(it) }.forEach { set.add(it) }
            }

        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }

        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }

        return sb.toString()
    }

    internal fun DataStore<Preferences>.readCookie(
        domain: String?
    ): String {
        var res = ""
        runBlocking {
            domain ?: return@runBlocking
            res = getValue(preferencesKey(domain), "").first()
        }
        return res
    }

    internal fun DataStore<Preferences>.saveCookie(
        url: String?,
        domain: String?,
        cookies: String
    ) {
        runBlocking {
            url ?: return@runBlocking
            setValue(preferencesKey(url), cookies)
            domain ?: return@runBlocking
            setValue(preferencesKey(domain), cookies)
        }

    }
}