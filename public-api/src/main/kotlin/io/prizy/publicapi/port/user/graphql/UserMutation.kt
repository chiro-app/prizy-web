package io.prizy.publicapi.port.user.graphql

import com.expediagroup.graphql.server.operations.Mutation
import graphql.schema.DataFetchingEnvironment
import io.prizy.domain.user.usecase.CreateUserUseCase
import io.prizy.domain.user.usecase.DeleteUserUseCase
import io.prizy.domain.user.usecase.UpdateUserAddressUseCase
import io.prizy.domain.user.usecase.UpdateUserPreferencesUseCase
import io.prizy.domain.user.usecase.UpdateUserUseCase
import io.prizy.graphql.context.principal
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.publicapi.port.user.graphql.dto.AddressDto
import io.prizy.publicapi.port.user.graphql.dto.CreateUserDto
import io.prizy.publicapi.port.user.graphql.dto.UpdateAddressDto
import io.prizy.publicapi.port.user.graphql.dto.UpdateUserDto
import io.prizy.publicapi.port.user.graphql.dto.UpdateUserPreferencesDto
import io.prizy.publicapi.port.user.graphql.dto.UserDto
import io.prizy.publicapi.port.user.graphql.dto.UserPreferencesDto
import io.prizy.publicapi.port.user.mapper.AddressDtoMapper
import io.prizy.publicapi.port.user.mapper.CreateUserDtoMapper
import io.prizy.publicapi.port.user.mapper.UpdateAddressDtoMapper
import io.prizy.publicapi.port.user.mapper.UpdateUserDtoMapper
import io.prizy.publicapi.port.user.mapper.UpdateUserPreferencesDtoMapper
import io.prizy.publicapi.port.user.mapper.UserDtoMapper
import io.prizy.publicapi.port.user.mapper.UserPreferencesDtoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 5:43 PM
 */

@Component
class UserMutation(
  private val updateUserUseCase: UpdateUserUseCase,
  private val createUserUseCase: CreateUserUseCase,
  private val deleteUserUseCase: DeleteUserUseCase,
  private val updateUserAddressUseCase: UpdateUserAddressUseCase,
  private val updateUserPreferencesUseCase: UpdateUserPreferencesUseCase,
) : Mutation {

  suspend fun createUser(request: CreateUserDto): UserDto = withContext(Dispatchers.IO) {
    createUserUseCase
      .create(CreateUserDtoMapper.map(request))
      .let(UserDtoMapper::map)
  }

  @AuthorizedDirective
  suspend fun updateUser(dfe: DataFetchingEnvironment, request: UpdateUserDto): UserDto =
    withContext(Dispatchers.IO) {
      updateUserUseCase
        .update(UpdateUserDtoMapper.map(request, dfe.principal().id))
        .let(UserDtoMapper::map)
    }

  @AuthorizedDirective
  suspend fun updateAddress(dfe: DataFetchingEnvironment, request: UpdateAddressDto): AddressDto =
    withContext(Dispatchers.IO) {
      updateUserAddressUseCase
        .update(UpdateAddressDtoMapper.map(dfe.principal().id, request))
        .let(AddressDtoMapper::map)
    }

  @AuthorizedDirective
  @Deprecated("To be removed, devices will be manged by onesignal")
  suspend fun registerDevice(dfe: DataFetchingEnvironment, deviceId: String): Boolean =
    withContext(Dispatchers.IO) {
      true
    }

  @Deprecated("To be removed, devices will be manged by onesignal")
  suspend fun unregisterDevice(deviceId: String): Boolean =
    withContext(Dispatchers.IO) {
      true
    }

  @AuthorizedDirective
  suspend fun updatePreferences(
    dfe: DataFetchingEnvironment,
    request: UpdateUserPreferencesDto
  ): UserPreferencesDto =
    withContext(Dispatchers.IO) {
      updateUserPreferencesUseCase
        .update(UpdateUserPreferencesDtoMapper.map(request, dfe.principal().id))
        .let(UserPreferencesDtoMapper::map)
    }

  @AuthorizedDirective
  suspend fun deleteUser(dfe: DataFetchingEnvironment): Boolean = withContext(Dispatchers.IO) {
    deleteUserUseCase.delete(dfe.principal().id)
  }
}