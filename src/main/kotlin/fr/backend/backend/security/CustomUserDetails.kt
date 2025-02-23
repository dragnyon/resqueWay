
package fr.backend.backend.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

data class CustomUserDetails(
    val id: UUID,
    val email: String,
    val entrepriseId: UUID?,
    val typeUtilisateur: String,
    private val password: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> = authorities
    override fun getPassword(): String = password
    override fun getUsername(): String = email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}
