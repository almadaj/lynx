package com.lynx.api.service;

import com.lynx.api.model.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ResourceService {

    private final Map<Long, Resource> resources = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Resource> getAllResources() {
        return new ArrayList<>(resources.values());
    }

    public Optional<Resource> getResourceById(Long id) {
        return Optional.ofNullable(resources.get(id));
    }

    public Resource createResource(Resource resource) {
        Long id = idGenerator.getAndIncrement();
        resource.setId(id);
        if (resource.getStatus() == null) {
            resource.setStatus("active");
        }
        resources.put(id, resource);
        return resource;
    }

    public Optional<Resource> updateResource(Long id, Resource updatedResource) {
        if (!resources.containsKey(id)) {
            return Optional.empty();
        }
        updatedResource.setId(id);
        resources.put(id, updatedResource);
        return Optional.of(updatedResource);
    }

    public boolean deleteResource(Long id) {
        return resources.remove(id) != null;
    }
}
