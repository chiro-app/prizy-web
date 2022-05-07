package io.prizy.adapters.user.persistence;

import io.prizy.domain.user.port.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nidhal Dogga
 * @created 5/7/2022 11:22 AM
 */


@Component
@Transactional
@RequiredArgsConstructor
public class DeviceRepositoryImpl implements DeviceRepository {


}
