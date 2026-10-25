import API_URL from "./api";

export async function getAccommodations() {
  const response = await fetch(`${API_URL}/accommodations`);

  if (!response.ok) {
    throw new Error("No fue posible obtener los alojamientos.");
  }

  return response.json();
}