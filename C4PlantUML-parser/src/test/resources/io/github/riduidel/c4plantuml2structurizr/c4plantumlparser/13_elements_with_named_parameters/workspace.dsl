workspace "C4_Elements" "" {
	model {
		properties {
			"structurizr.groupSeparator" "/"
		}
		personAlias = Person "A person" "This is a person" {
			url https://microformats.org/wiki/microformats2#person_example
		}
		systemAlias = softwareSystem "A system" "This is a system" {
			url https://docs.structurizr.com/dsl/language#softwaresystem
			containerAlias = container "A container" "This is a container" {
				description "This is a container"
				url "http://container.link"
				tags "Container tags,"
				technology "Container technology"
				componentAlias = component "A component" "This is a component" {
					technology "Component technology"
				}
			}
		}
		personAlias -> systemAlias "Person to system description" "This is person to system technology"
		personAlias -> containerAlias "A relationship between person and container" "This is person to container technology"
		personAlias -> componentAlias "A relationship between person and component" "This is person to component technology"
	}
	views {
		systemContext systemAlias context_of_systemAlias "Context view of \"A system\"" {
		}
		container systemAlias containers_of_systemAlias "Container view of \"A system\"" {
		}
		component containerAlias components_of_containerAlias "Component view of \"A container\"" {
		}
	}
}