package com.esmailelhanash.petfinder.models

import com.google.gson.annotations.SerializedName



data class AnimalResponse(
    @SerializedName("animals") val animals: List<Animal>,
    @SerializedName("pagination") val pagination: Pagination
)


data class Animal(
    @SerializedName("id") val id: String,
    @SerializedName("organization_id") val organizationId: String,
    @SerializedName("url") val url: String,
    @SerializedName("type") val type: String,
    @SerializedName("species") val species: String,
    @SerializedName("breeds") val breeds: Breeds,
    @SerializedName("colors") val colors: Colors,
    @SerializedName("age") val age: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("size") val size: String,
    @SerializedName("coat") val coat: String?,
    @SerializedName("attributes") val attributes: Attributes,
    @SerializedName("environment") val environment: Environment,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("organization_animal_id") val organizationAnimalId: String,
    @SerializedName("photos") val photos: List<Photo>,
    @SerializedName("primary_photo_cropped") val primaryPhotoCropped: Photo?,
    @SerializedName("videos") val videos: List<Video>,
    @SerializedName("status") val status: String,
    @SerializedName("status_changed_at") val statusChangedAt: String,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("distance") val distance: Double?,
    @SerializedName("contact") val contact: Contact,
    @SerializedName("_links") val links: Links
)

data class Breeds(
    @SerializedName("primary") val primary: String,
    @SerializedName("secondary") val secondary: String?,
    @SerializedName("mixed") val mixed: Boolean,
    @SerializedName("unknown") val unknown: Boolean
)

data class Colors(
    @SerializedName("primary") val primary: String?,
    @SerializedName("secondary") val secondary: String?,
    @SerializedName("tertiary") val tertiary: String?
)

data class Attributes(
    @SerializedName("spayed_neutered") val spayedNeutered: Boolean,
    @SerializedName("house_trained") val houseTrained: Boolean,
    @SerializedName("declawed") val declawed: Boolean?,
    @SerializedName("special_needs") val specialNeeds: Boolean,
    @SerializedName("shots_current") val shotsCurrent: Boolean
)

data class Environment(
    @SerializedName("children") val children: Boolean?,
    @SerializedName("dogs") val dogs: Boolean?,
    @SerializedName("cats") val cats: Boolean?
)

data class Contact(
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("address") val address: Address
)

data class Address(
    @SerializedName("address1") val address1: String?,
    @SerializedName("address2") val address2: String?,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("postcode") val postcode: String,
    @SerializedName("country") val country: String
)

data class Links(
    @SerializedName("self") val self: Self,
    @SerializedName("type") val type: Type,
    @SerializedName("organization") val organization: Organization
)

data class Self(
    @SerializedName("href") val href: String
)

data class Type(
    @SerializedName("href") val href: String
)

data class Organization(
    @SerializedName("href") val href: String
)

data class Photo(
    @SerializedName("small") val small: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("large") val large: String,
    @SerializedName("full") val full: String
)

data class Video(
    @SerializedName("embed") val embed: String
)

data class Pagination(
    @SerializedName("count_per_page") val countPerPage: Int,
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("_links") val links: Links
)


data class AnimalTypesResponse(
    @SerializedName("types")
    val animalTypes: List<AnimalType>
)

data class AnimalType(
    @SerializedName("name")
    val name: String,
    @SerializedName("coats")
    val coats: List<String>,
    @SerializedName("colors")
    val colors: List<String>,
    @SerializedName("genders")
    val genders: List<String>,
    @SerializedName("_links")
    val typeLinks: TypeLinks
)

data class TypeLinks(
    @SerializedName("self")
    val selfLink: Link,
    @SerializedName("breeds")
    val breedsLink: Link
)

data class Link(
    @SerializedName("href")
    val href: String
)
