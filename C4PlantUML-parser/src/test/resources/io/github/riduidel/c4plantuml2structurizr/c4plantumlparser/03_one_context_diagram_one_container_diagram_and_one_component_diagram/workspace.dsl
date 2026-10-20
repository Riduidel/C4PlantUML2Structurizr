workspace "C4_Elements" "" {
	model {
		personAlias = Person "A person" "This is a person"
		systemAlias = softwareSystem "A system" "This is a system" {
			containerAlias = container "A container" "This is a container" {
				technology "Container technology"
				componentAlias = component "A component" "This is a component" {
					technology "Component technology"
				}
			}
		}
		personAlias -> systemAlias "A relationship between person and system" "This is person to system technology"
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