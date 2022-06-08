package io.prizy.publicapi.port.user.graphql

import com.expediagroup.graphql.server.operations.Mutation
import io.prizy.domain.user.service.DeviceService
import io.prizy.domain.user.usecase.CreateUserUseCase
import io.prizy.domain.user.usecase.UpdateUserAddressUseCase
import io.prizy.domain.user.usecase.UpdateUserPreferencesUseCase
import io.prizy.domain.user.usecase.UpdateUserUseCase
import io.prizy.graphql.context.GraphQLContext
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
  private val deviceService: DeviceService,
  private val updateUserUseCase: UpdateUserUseCase,
  private val createUserUseCase: CreateUserUseCase,
  private val updateUserAddressUseCase: UpdateUserAddressUseCase,
  private val updateUserPreferencesUseCase: UpdateUserPreferencesUseCase
) : Mutation {

  suspend fun createUser(request: CreateUserDto): UserDto = withContext(Dispatchers.IO) {
    createUserUseCase
      .create(CreateUserDtoMapper.map(request))
      .let(UserDtoMapper::map)
  }

  @AuthorizedDirective
  suspend fun updateUser(ctx: GraphQLContext.Authenticated, request: UpdateUserDto): UserDto =
    withContext(Dispatchers.IO) {
      updateUserUseCase
        .update(UpdateUserDtoMapper.map(request, ctx.principal.id))
        .let(UserDtoMapper::map)
    }

  @AuthorizedDirective
  suspend fun updateAddress(ctx: GraphQLContext.Authenticated, request: UpdateAddressDto): AddressDto =
    withContext(Dispatchers.IO) {
      updateUserAddressUseCase
        .update(UpdateAddressDtoMapper.map(ctx.principal.id, request))
        .let(AddressDtoMapper::map)
    }

  @AuthorizedDirective
  suspend fun registerDevice(ctx: GraphQLContext.Authenticated, deviceId: String): Boolean =
    withContext(Dispatchers.IO) {
      deviceService.register(ctx.principal.id, deviceId)
    }

  suspend fun unregisterDevice(deviceId: String): Boolean =
    withContext(Dispatchers.IO) {
      deviceService.unregister(deviceId)
    }

  @AuthorizedDirective
  suspend fun updatePreferences(
    ctx: GraphQLContext.Authenticated,
    request: UpdateUserPreferencesDto
  ): UserPreferencesDto =
    withContext(Dispatchers.IO) {
      updateUserPreferencesUseCase
        .update(UpdateUserPreferencesDtoMapper.map(request, ctx.principal.id))
        .let(UserPreferencesDtoMapper::map)
    }
}