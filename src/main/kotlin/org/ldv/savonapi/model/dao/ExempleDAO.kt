package org.ldv.savonapi.model.dao;

import org.ldv.savonapi.model.entity.Exemple
import org.springframework.data.jpa.repository.JpaRepository

interface ExempleDAO : JpaRepository<Exemple, Long> {
}