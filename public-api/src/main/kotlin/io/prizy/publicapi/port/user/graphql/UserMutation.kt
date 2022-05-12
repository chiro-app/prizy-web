package io.prizy.publicapi.port.user.graphql

import com.expediagroup.graphql.server.operations.Mutation
import io.prizy.domain.user.service.AddressService
import io.prizy.domain.user.service.DeviceService
import io.prizy.domain.user.service.UserPreferencesService
import io.prizy.domain.user.service.UserService
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
  private val userService: UserService,
  private val deviceService: DeviceService,
  private val addressService: AddressService,
  private val userPreferencesService: UserPreferencesService
) : Mutation {

  suspend fun createUser(request: CreateUserDto): UserDto = withContext(Dispatchers.IO) {
    userService
      .createUser(CreateUserDtoMapper.map(request))
      .let(UserDtoMapper::map)
  }

  @AuthorizedDirective
  suspend fun updateUser(ctx: GraphQLContext.Authenticated, request: UpdateUserDto): UserDto =
    withContext(Dispatchers.IO) {
      userService
        .updateUser(UpdateUserDtoMapper.map(request, ctx.principal.id))
        .let(UserDtoMapper::map)
    }

  @AuthorizedDirective
  suspend fun updateAddress(ctx: GraphQLContext.Authenticated, request: UpdateAddressDto): AddressDto =
    withContext(Dispatchers.IO) {
      addressService
        .updateUserAddress(ctx.principal.id, UpdateAddressDtoMapper.map(request))
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
      userPreferencesService
        .update(UpdateUserPreferencesDtoMapper.map(request, ctx.principal.id))
        .let(UserPreferencesDtoMapper::map)
    }
}