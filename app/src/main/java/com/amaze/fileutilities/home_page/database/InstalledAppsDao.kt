/*
 * Copyright (C) 2021-2024 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
 * Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.
 *
 * This file is part of Amaze File Utilities.
 *
 * Amaze File Utilities is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.amaze.fileutilities.home_page.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface InstalledAppsDao {

    @Query("SELECT * FROM installedapps WHERE package_name=:packageName")
    fun findByPackageName(packageName: String): InstalledApps?

    @Query("SELECT * FROM installedapps")
    fun findAll(): List<InstalledApps>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(installedApp: InstalledApps): Long

    @Update
    fun update(installedApp: InstalledApps)

    @Transaction
    fun updateOrInsert(installedAppsList: List<InstalledApps>) {
        for (installedApp in installedAppsList) {
            val rowId = insert(installedApp)
            if (rowId == -1L) {
                // there is already an entry so we want to update
                update(installedApp)
            }
        }
    }

    @Query("DELETE FROM installedapps")
    fun deleteAll()
}
